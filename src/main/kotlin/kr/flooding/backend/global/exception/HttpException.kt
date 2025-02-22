package kr.flooding.backend.global.exception

open class HttpException(
	val exceptionEnum: ExceptionEnum,
) : RuntimeException(exceptionEnum.reason)
