package kr.flooding.backend.domain.file.usecase

import kr.flooding.backend.domain.file.dto.response.UploadImageResponse
import kr.flooding.backend.global.exception.ExceptionEnum
import kr.flooding.backend.global.exception.HttpException
import kr.flooding.backend.global.exception.toPair
import kr.flooding.backend.global.util.ImageUtil
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import software.amazon.awssdk.core.sync.RequestBody
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import java.time.LocalDateTime
import java.util.UUID.randomUUID

@Service
@Transactional
class UploadImageUsecase(
	private val s3Client: S3Client,
	private val imageUtil: ImageUtil,
) {
	@Value("\${cloud.aws.s3.bucket-name}")
	lateinit var bucketName: String

	fun execute(images: List<MultipartFile>): UploadImageResponse {
		val imageExtensions = listOf("png", "jpg", "jpeg", "gif")
		val imageUrls =
			images.map {
				val originalName = requireNotNull(it.originalFilename)
				val extension = originalName.split(".").last()

				if (!imageExtensions.contains(extension)) {
					throw HttpException(ExceptionEnum.FILE.INVALID_IMAGE_EXTENSION.toPair())
				}

				val filename = "${LocalDateTime.now()}:${randomUUID()}.webp"
				val key = "images/$filename"

				val webpImage = imageUtil.convertToWebp(filename, it)

				val putObjectRequest =
					PutObjectRequest
						.builder()
						.bucket(bucketName)
						.key(key)
						.build()
				val requestBody = RequestBody.fromInputStream(webpImage.inputStream(), webpImage.size.toLong())

				s3Client.putObject(putObjectRequest, requestBody)

				key
			}

		return UploadImageResponse(imageUrls)
	}
}
