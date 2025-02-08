package team.gsm.flooding.global.exception.dto

import org.springframework.http.HttpStatus
import team.gsm.flooding.global.exception.ExceptionEnum

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
