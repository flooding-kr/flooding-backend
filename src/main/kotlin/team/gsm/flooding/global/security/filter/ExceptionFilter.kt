package team.gsm.flooding.global.security.filter

import com.fasterxml.jackson.core.json.JsonWriteFeature
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import jakarta.ws.rs.core.MediaType
import org.springframework.web.filter.OncePerRequestFilter
import team.gsm.flooding.global.exception.ExceptionEnum
import team.gsm.flooding.global.exception.HttpException
import team.gsm.flooding.global.exception.dto.HttpExceptionResponse

class ExceptionFilter : OncePerRequestFilter() {
	override fun doFilterInternal(
		request: HttpServletRequest,
		response: HttpServletResponse,
		filterChain: FilterChain,
	) {
		try {
			filterChain.doFilter(request, response)
		} catch (exception: Exception) {
			val exceptionResponse =
				if (exception is HttpException) {
					HttpExceptionResponse(exception.exceptionEnum)
				} else {
					HttpExceptionResponse(ExceptionEnum.UNKNOWN_SERVER_ERROR)
				}

			val objectMapper =
				jacksonObjectMapper()
					.configure(JsonWriteFeature.ESCAPE_NON_ASCII.mappedFeature(), true)
					.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)

			response.status = exceptionResponse.status.value()
			response.writer.write(objectMapper.writeValueAsString(exceptionResponse))
			response.characterEncoding = "UTF-8"
			response.contentType = MediaType.APPLICATION_JSON
		}
	}
}
