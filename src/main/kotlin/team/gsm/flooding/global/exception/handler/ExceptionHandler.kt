package team.gsm.flooding.global.exception.handler

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import team.gsm.flooding.global.exception.HttpException
import team.gsm.flooding.global.exception.dto.HttpExceptionResponse

@RestControllerAdvice
class ExceptionHandler {
	@ExceptionHandler(HttpException::class)
	fun httpException(exception: HttpException): ResponseEntity<HttpExceptionResponse> {
		val response = HttpExceptionResponse(exception.exceptionEnum)
		return ResponseEntity.status(response.status).body(response)
	}

	@ExceptionHandler(RuntimeException::class)
	fun runtimeException(exception: RuntimeException): ResponseEntity<HttpExceptionResponse> {
		val response = HttpExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR, exception.message ?: "")
		return ResponseEntity.status(response.status).body(response)
	}
}
