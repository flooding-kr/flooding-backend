package kr.flooding.backend.global.security.configuration

import kr.flooding.backend.global.properties.CorsProperties
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class CorsConfig(
	private val corsProperties: CorsProperties
) : WebMvcConfigurer {
	override fun addCorsMappings(registry: CorsRegistry) {
		registry
			.addMapping("/**")
			.allowedOrigins(*corsProperties.allowedOrigins.toTypedArray())
			.allowedHeaders("*")
			.allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
			.exposedHeaders("Authorization", "Refresh-Token")
			.allowCredentials(true)
	}
}
