package kr.flooding.backend.global.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "cors")
class CorsProperties (
    var allowedOrigins: List<String>
)

