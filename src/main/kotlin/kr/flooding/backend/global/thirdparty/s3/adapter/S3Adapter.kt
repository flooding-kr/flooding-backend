package kr.flooding.backend.global.thirdparty.s3.adapter

import kr.flooding.backend.domain.file.shared.PresignedUrlModel
import kr.flooding.backend.global.properties.AwsProperties
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.model.GetObjectRequest
import software.amazon.awssdk.services.s3.presigner.S3Presigner
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest
import java.time.Duration

@Component
class S3Adapter (
	private val awsProperties: AwsProperties,
	private val redisTemplate: RedisTemplate<String, String>
) {
	fun generatePresignedUrl(key: String): PresignedUrlModel {
		return PresignedUrlModel(
			key = key,
			presignedUrl = getCachedPresignedUrl(key) ?: getAndSavePresignedUrl(key)
		)
	}

	private fun getCachedPresignedUrl(key: String): String? =
		redisTemplate.opsForValue().get("presigned-url:$key")

	private fun getAndSavePresignedUrl(key: String): String {
		val presignedUrl = getPresignedUrl(key)
		redisTemplate.opsForValue().set("presigned-url:$key", presignedUrl, Duration.ofHours(1))

		return presignedUrl
	}

	private fun getPresignedUrl(key: String): String{
		val credentials = AwsBasicCredentials.create(
			awsProperties.s3.accessKeyId,
			awsProperties.s3.secretAccessKey,
		)

		val presigner = S3Presigner
			.builder()
			.region(Region.of(awsProperties.region))
			.credentialsProvider(StaticCredentialsProvider.create(credentials))
			.build()

		val getObjectRequest = GetObjectRequest
			.builder()
			.bucket(awsProperties.s3.bucketName)
			.key(key)
			.build()

		val presignRequest = GetObjectPresignRequest
			.builder()
			.signatureDuration(Duration.ofHours(1))
			.getObjectRequest(getObjectRequest)
			.build()

		val presignedRequest = presigner.presignGetObject(presignRequest)
		presigner.close()

		return presignedRequest.url().toString()
	}
}