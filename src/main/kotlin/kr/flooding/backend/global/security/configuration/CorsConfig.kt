package kr.flooding.backend.global.security.configuration

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class CorsConfig : WebMvcConfigurer {
	override fun addCorsMappings(registry: CorsRegistry) {
		registry
			.addMapping("/**")
			.allowedOriginPatterns("*")
			.allowedHeaders("*")
			.allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
			.exposedHeaders("Authorization", "Refresh-Token")
			.allowCredentials(true)
	}
}
