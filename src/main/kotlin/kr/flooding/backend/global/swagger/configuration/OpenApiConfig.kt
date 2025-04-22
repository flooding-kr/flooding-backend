package kr.flooding.backend.global.swagger.configuration

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.servers.Server
import org.springframework.context.annotation.Configuration

@OpenAPIDefinition(
	servers = [
		Server(url = "https://api.flooding.kr/v1", description = "Production Server"),
		Server(url = "http://localhost:8080/v1", description = "Local Server"),
		Server(url = "/v1", description = "Current Server"),
	]
)
@Configuration
class OpenApiConfig