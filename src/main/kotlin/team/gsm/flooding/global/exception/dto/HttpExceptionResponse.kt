package team.gsm.flooding.global.exception.dto

import team.gsm.flooding.global.exception.ExceptionEnum

data class HttpExceptionResponse (val exceptionEnum: ExceptionEnum){
	val status: Int = exceptionEnum.status.value()
	val reason: String = exceptionEnum.reason
}