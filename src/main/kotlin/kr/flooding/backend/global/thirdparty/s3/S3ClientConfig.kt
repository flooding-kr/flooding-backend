package kr.flooding.backend.global.thirdparty.s3

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.S3Client

@Configuration
class S3ClientConfig(
	@Value("\${cloud.aws.s3.access-key-id}")
	private val accessKeyId: String,
	@Value("\${cloud.aws.s3.secret-access-key}")
	private val secretAccessKey: String,
	@Value("\${cloud.aws.region}")
	private val region: String,
) {
	@Bean
	fun s3Client(): S3Client {
		val credentials = AwsBasicCredentials.create(accessKeyId, secretAccessKey)

		return S3Client
			.builder()
			.region(Region.of(region))
			.credentialsProvider(StaticCredentialsProvider.create(credentials))
			.build()
	}
}
