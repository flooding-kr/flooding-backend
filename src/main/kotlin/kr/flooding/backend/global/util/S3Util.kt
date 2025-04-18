package kr.flooding.backend.global.util

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.model.GetObjectRequest
import software.amazon.awssdk.services.s3.presigner.S3Presigner
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest
import java.time.Duration

@Component
class S3Util(
	@Value("\${cloud.aws.s3.access-key-id}")
	private val accessKeyId: String,
	@Value("\${cloud.aws.s3.secret-access-key}")
	private val secretAccessKey: String,
	@Value("\${cloud.aws.region}")
	private val region: String,
	@Value("\${cloud.aws.s3.bucket-name}")
	private val bucketName: String,
) {
	val signatureDuration: Duration = Duration.ofHours(1)

	fun generatePresignedUrl(key: String): String {
		val credentials = AwsBasicCredentials.create(accessKeyId, secretAccessKey)

		val presigner =
			S3Presigner
				.builder()
				.region(Region.of(region))
				.credentialsProvider(StaticCredentialsProvider.create(credentials))
				.build()

		val getObjectRequest =
			GetObjectRequest
				.builder()
				.bucket(bucketName)
				.key(key)
				.build()

		val presignRequest =
			GetObjectPresignRequest
				.builder()
				.signatureDuration(signatureDuration)
				.getObjectRequest(getObjectRequest)
				.build()

		val presignedRequest = presigner.presignGetObject(presignRequest)

		presigner.close()
		return presignedRequest.url().toString()
	}
}
