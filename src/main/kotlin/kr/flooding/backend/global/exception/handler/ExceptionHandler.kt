package kr.flooding.backend.global.exception.handler

import kr.flooding.backend.global.exception.ExceptionEnum
import kr.flooding.backend.global.exception.HttpException
import kr.flooding.backend.global.exception.dto.HttpExceptionResponse
import kr.flooding.backend.global.exception.toPair
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingServletRequestParameterException
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

	@ExceptionHandler(HttpMessageNotReadableException::class)
	fun httpMessageNotReadableException(
		exception: HttpMessageNotReadableException
	): ResponseEntity<HttpExceptionResponse> {
		val response = HttpExceptionResponse(HttpStatus.BAD_REQUEST, "잘못된 바디 구성입니다.")
		return ResponseEntity.status(response.status).body(response)
	}

	@ExceptionHandler(MissingServletRequestParameterException::class)
	fun missingServletRequestParameterException(
		exception: MissingServletRequestParameterException
	): ResponseEntity<HttpExceptionResponse> {
		val response = HttpExceptionResponse(HttpStatus.BAD_REQUEST, "잘못된 파라미터 구성입니다.")
		return ResponseEntity.status(response.status).body(response)
	}

	@ExceptionHandler(Exception::class)
	fun exception(exception: Exception): ResponseEntity<HttpExceptionResponse> {
		val response = HttpExceptionResponse(ExceptionEnum.UNKNOWN.UNKNOWN_SERVER_ERROR.toPair())
		return ResponseEntity.status(response.status).body(response)
	}
}
