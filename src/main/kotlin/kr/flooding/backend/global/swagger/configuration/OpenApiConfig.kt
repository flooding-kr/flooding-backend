package kr.flooding.backend.global.swagger.configuration

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.servers.Server
import org.springframework.context.annotation.Configuration

@OpenAPIDefinition(
	servers =
		[
			Server(url = "https://api.flooding.kr/v1", description = "Deployment Server"),
			Server(url = "http://localhost:8080/v1", description = "Local Server"),
			Server(url = "http://upper-alb-2111740417.ap-northeast-2.elb.amazonaws.com/v1", description = "Test Server"),
		],
)
@Configuration
class OpenApiConfig
