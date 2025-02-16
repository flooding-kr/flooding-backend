package team.gsm.flooding.global.exception

open class HttpException(
	val exceptionEnum: ExceptionEnum,
) : RuntimeException(exceptionEnum.reason)
