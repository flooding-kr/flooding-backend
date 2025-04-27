package kr.flooding.backend.domain.file.usecase

import kr.flooding.backend.domain.file.dto.common.response.UploadImageResponse
import kr.flooding.backend.domain.file.dto.web.response.UploadImageListResponse
import kr.flooding.backend.global.exception.ExceptionEnum
import kr.flooding.backend.global.exception.HttpException
import kr.flooding.backend.global.exception.toPair
import kr.flooding.backend.global.properties.AwsProperties
import kr.flooding.backend.global.thirdparty.s3.adapter.S3Adapter
import kr.flooding.backend.global.util.FileUtil
import kr.flooding.backend.global.util.FileUtil.Companion.isImageExtension
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import software.amazon.awssdk.core.exception.SdkClientException
import software.amazon.awssdk.core.sync.RequestBody
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import software.amazon.awssdk.services.s3.model.S3Exception
import java.time.LocalDateTime
import java.util.UUID.randomUUID
import java.util.concurrent.Executors

@Service
@Transactional
class UploadImageUsecase(
	private val s3Client: S3Client,
	private val awsProperties: AwsProperties,
	private val s3Adapter: S3Adapter,
	private val fileUtil: FileUtil,
) {
	fun execute(images: List<MultipartFile>): UploadImageListResponse {
		images.forEach {
			if(!it.isImageExtension()){
				throw HttpException(ExceptionEnum.FILE.INVALID_IMAGE_EXTENSION.toPair())
			}
		}

		Executors.newVirtualThreadPerTaskExecutor().use { executor ->
			val futures = images.map {
				executor.submit<UploadImageResponse> { uploadImage(it) }
			}
			val imageUrls = futures.map { it.get() }
			return UploadImageListResponse(imageUrls)
		}
	}

	fun uploadImage(image: MultipartFile): UploadImageResponse {
		val filename = "${LocalDateTime.now()}:${randomUUID()}.webp"
		val key = "images/$filename"

		val webpImage = fileUtil.convertToWebp(filename, image)

		try {
			val putObjectRequest =
				PutObjectRequest
					.builder()
					.bucket(awsProperties.s3.bucketName)
					.key(key)
					.build()
			val requestBody = RequestBody.fromInputStream(webpImage.inputStream(), webpImage.size.toLong())

			s3Client.putObject(putObjectRequest, requestBody)

			return UploadImageResponse(
				key = key,
				presignedUrl = s3Adapter.generatePresignedUrl(key)
			)
		} catch (e: SdkClientException) {
			throw HttpException(ExceptionEnum.FILE.FAILED_TO_UPLOAD_FILE.toPair())
		} catch (e: S3Exception) {
			throw HttpException(ExceptionEnum.FILE.FAILED_TO_UPLOAD_FILE.toPair())
		}
	}
}
