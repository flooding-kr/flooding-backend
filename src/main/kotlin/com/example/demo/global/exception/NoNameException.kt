package com.example.demo.global.exception

open class NoNameException(val exceptionEnum: ExceptionEnum): RuntimeException(exceptionEnum.reason)