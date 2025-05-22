package kr.flooding.backend.global.exception.handler

import kr.flooding.backend.global.exception.HttpException
import kr.flooding.backend.global.exception.dto.HttpExceptionResponse
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

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

	@ExceptionHandler(MethodArgumentNotValidException::class)
	fun methodArgumentNotValidException(
		exception: MethodArgumentNotValidException,
	): ResponseEntity<HttpExceptionResponse> {
		val response = HttpExceptionResponse(HttpStatus.BAD_REQUEST, exception.fieldError?.defaultMessage ?: "")
		return ResponseEntity.status(response.status).body(response)
	}

	@ExceptionHandler(DataIntegrityViolationException::class)
	fun dataIntegrityViolationException(
		exception: DataIntegrityViolationException,
	): ResponseEntity<HttpExceptionResponse> {
		val response = HttpExceptionResponse(HttpStatus.BAD_REQUEST, "이미 처리된 요청입니다.")
		return ResponseEntity.status(response.status).body(response)
	}
}
