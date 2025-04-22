package kr.flooding.backend.global.properties

import kr.flooding.backend.global.properties.model.S3Model
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@ConfigurationProperties(prefix = "cloud.aws")
class AwsProperties (
    val region: String,
    val s3: S3Model
)
