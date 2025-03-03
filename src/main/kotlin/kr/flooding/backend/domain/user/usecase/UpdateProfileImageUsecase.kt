package kr.flooding.backend.domain.user.usecase

import kr.flooding.backend.global.util.UserUtil
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
class UpdateProfileImageUsecase(
	private val userUtil: UserUtil,
	private val s3Client: S3Client,
) {
	@Value("\${cloudflare.r2.bucket-name}")
	lateinit var bucketName: String

	@Value("\${cloudflare.r2.public-url}")
	lateinit var publicUrl: String

	fun execute(image: MultipartFile) {
		val user = userUtil.getUser()

		val originalName = requireNotNull(image.originalFilename)
		val extension = originalName.split(".").last()
		val filename = "${LocalDateTime.now()}:${randomUUID()}.$extension"

		val putObjectRequest =
			PutObjectRequest
				.builder()
				.bucket(bucketName)
				.key(filename)
				.build()
		val requestBody = RequestBody.fromInputStream(image.inputStream, image.size)

		s3Client.putObject(putObjectRequest, requestBody)

		user.updateProfileImage("$publicUrl/$filename")
	}
}
