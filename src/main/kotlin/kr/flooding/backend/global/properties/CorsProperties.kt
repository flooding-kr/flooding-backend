package kr.flooding.backend.global.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "cors")
class CorsProperties {
    lateinit var allowedOrigins: List<String>
}

