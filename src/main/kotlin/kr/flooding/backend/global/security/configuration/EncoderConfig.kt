package kr.flooding.backend.global.security.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
class EncoderConfig {
	@Bean
	fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder(12)
}
