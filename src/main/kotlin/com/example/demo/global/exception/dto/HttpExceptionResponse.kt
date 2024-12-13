package com.example.demo.global.exception.dto

import com.example.demo.global.exception.ExceptionEnum

data class HttpExceptionResponse (val exceptionEnum: ExceptionEnum){
	val status: Int = exceptionEnum.status.value()
	val reason: String = exceptionEnum.reason
}