package kr.flooding.backend.global.thirdparty.s3

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.core.checksums.RequestChecksumCalculation
import software.amazon.awssdk.core.checksums.ResponseChecksumValidation
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.S3Configuration
import java.net.URI

@Configuration
class S3ClientConfig(
	@Value("\${cloudflare.r2.access-key-id}")
	private val accessKeyId: String,
	@Value("\${cloudflare.r2.secret-access-key}")
	private val secretAccessKey: String,
	@Value("\${cloudflare.r2.endpoint}")
	private val endpoint: String,
) {
	@Bean
	fun s3Client(): S3Client {
		val credentials = AwsBasicCredentials.create(accessKeyId, secretAccessKey)
		val configuration =
			S3Configuration
				.builder()
				.pathStyleAccessEnabled(true)
				.build()

		return S3Client
			.builder()
			.endpointOverride(URI.create(endpoint))
			.credentialsProvider(StaticCredentialsProvider.create(credentials))
			.region(Region.of("auto"))
			.serviceConfiguration(configuration)
			.responseChecksumValidation(ResponseChecksumValidation.WHEN_REQUIRED)
			.requestChecksumCalculation(RequestChecksumCalculation.WHEN_REQUIRED)
			.build()
	}
}
