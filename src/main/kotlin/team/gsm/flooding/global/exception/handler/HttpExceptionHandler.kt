package team.gsm.flooding.global.exception.handler

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import team.gsm.flooding.global.exception.ExpectedException
import team.gsm.flooding.global.exception.dto.HttpExceptionResponse

@RestControllerAdvice
class HttpExceptionHandler(
	private val objectMapper: ObjectMapper,
) {
	@ExceptionHandler(ExpectedException::class)
	fun httpException(exception: ExpectedException): ResponseEntity<HttpExceptionResponse> {
		val response = HttpExceptionResponse(exception.exceptionEnum)
		return ResponseEntity.status(response.status).body(response)
	}
}
