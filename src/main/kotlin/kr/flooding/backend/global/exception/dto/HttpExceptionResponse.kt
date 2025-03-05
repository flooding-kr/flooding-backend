package kr.flooding.backend.global.exception.dto

import org.springframework.http.HttpStatus

data class HttpExceptionResponse(
	val status: HttpStatus,
	val reason: String,
) {
	constructor(exceptionEnum: Pair<HttpStatus, String>) : this(
		status = exceptionEnum.first,
		reason = exceptionEnum.second,
	)
}
