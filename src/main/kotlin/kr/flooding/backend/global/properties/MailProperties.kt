package kr.flooding.backend.global.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "spring.mail")
class MailProperties (
    val host: String,
    val username: String,
    val password: String
)
