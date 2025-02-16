package team.gsm.flooding.domain.file.usecase

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import software.amazon.awssdk.core.sync.RequestBody
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import team.gsm.flooding.domain.file.dto.response.UploadImageResponse
import java.time.LocalDateTime
import java.util.UUID.randomUUID

@Service
@Transactional
class UploadImageUsecase(
	private val s3Client: S3Client,
) {
	@Value("\${cloudflare.r2.bucket-name}")
	lateinit var bucketName: String

	@Value("\${cloudflare.r2.public-url}")
	lateinit var publicUrl: String

	fun execute(images: List<MultipartFile>): UploadImageResponse {
		val imageUrls =
			images.map {
				val originalName = requireNotNull(it.originalFilename)
				val extension = originalName.split(".").last()
				val filename = "${LocalDateTime.now()}:${randomUUID()}.$extension"

				val putObjectRequest =
					PutObjectRequest
						.builder()
						.bucket(bucketName)
						.key(filename)
						.build()
				val requestBody = RequestBody.fromInputStream(it.inputStream, it.size)

				s3Client.putObject(putObjectRequest, requestBody)

				"$publicUrl/$filename"
			}

		return UploadImageResponse(imageUrls)
	}
}
