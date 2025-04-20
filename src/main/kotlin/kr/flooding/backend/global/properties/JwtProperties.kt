package kr.flooding.backend.global.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "jwt")
class JwtProperties {
    lateinit var accessTokenKey: String
    var accessTokenExpires: Long = 0
    lateinit var refreshTokenKey: String
    var refreshTokenExpires: Long = 0
}

