package kr.flooding.backend.global.swagger.configuration

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.ExternalDocumentation
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.License
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig {
	@Bean
	fun openAPI(): OpenAPI =
		OpenAPI()
			.components(
				Components().addSecuritySchemes(
					"bearerAuth",
					SecurityScheme()
						.type(SecurityScheme.Type.HTTP)
						.scheme("bearer")
						.bearerFormat("JWT"),
				),
			).addSecurityItem(
				SecurityRequirement().addList("bearerAuth"),
			).info(
				Info()
					.title("Flooding Server API Specification")
					.version("1.0.0")
					.license(
						License()
							.name("Apache 2.0")
							.url("http://www.apache.org/licenses/LICENSE-2.0.html"),
					),
			).externalDocs(
				ExternalDocumentation()
					.description("Github")
					.url("https://github.com/flooding-kr"),
			)
}
