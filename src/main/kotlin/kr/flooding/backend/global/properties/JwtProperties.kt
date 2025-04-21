package kr.flooding.backend.global.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@ConfigurationProperties(prefix = "jwt")
class JwtProperties (
    val accessTokenKey: String,
    val accessTokenExpires: Long,
    val refreshTokenKey: String,
    val refreshTokenExpires: Long
)

