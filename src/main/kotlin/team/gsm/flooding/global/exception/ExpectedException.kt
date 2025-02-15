package team.gsm.flooding.global.exception

open class ExpectedException(val exceptionEnum: ExceptionEnum) : RuntimeException(exceptionEnum.reason)
