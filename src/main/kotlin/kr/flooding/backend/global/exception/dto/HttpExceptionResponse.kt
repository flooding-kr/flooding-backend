package kr.flooding.backend.global.exception.dto

import kr.flooding.backend.global.exception.ExceptionEnum
import org.springframework.http.HttpStatus

data class HttpExceptionResponse(
	val exceptionEnum: ExceptionEnum?,
	val status: HttpStatus,
	val reason: String,
) {
	constructor(exceptionEnum: ExceptionEnum) : this(
		exceptionEnum = exceptionEnum,
		status = exceptionEnum.status,
		reason = exceptionEnum.reason,
	)

	constructor(status: HttpStatus, reason: String) : this(
		exceptionEnum = null,
		status = status,
		reason = reason,
	)
}
