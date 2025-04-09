package kr.flooding.backend.global.security.configuration

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class CorsConfig : WebMvcConfigurer {
	override fun addCorsMappings(registry: CorsRegistry) {
		val allowedOrigins = listOf("http://localhost:3000", "https://flooding.kr", "http://flooding.kr")
		registry
			.addMapping("/**")
			.allowedOrigins(*allowedOrigins.toTypedArray())
			.allowedOriginPatterns("*")
			.allowedHeaders("*")
			.allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
			.exposedHeaders("Authorization", "Refresh-Token")
			.allowCredentials(true)
	}
}
