package kr.flooding.backend.global.thirdparty.s3.config

import kr.flooding.backend.global.properties.AwsProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.S3Client

@Configuration
class S3Config(
	private val awsProperties: AwsProperties,
) {
	@Bean
	fun s3Client(): S3Client {
		val credentials = AwsBasicCredentials.create(
			awsProperties.s3.accessKeyId,
			awsProperties.s3.secretAccessKey
		)

		return S3Client
			.builder()
			.region(Region.of(awsProperties.region))
			.credentialsProvider(StaticCredentialsProvider.create(credentials))
			.build()
	}
}
