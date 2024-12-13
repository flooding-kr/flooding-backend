package com.example.demo.global.exception.handler

import com.example.demo.global.exception.NoNameException
import com.example.demo.global.exception.dto.HttpExceptionResponse
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
class HttpExceptionHandler (
	private val objectMapper: ObjectMapper
) {
	@ExceptionHandler(NoNameException::class)
	fun httpException(exception: NoNameException): ResponseEntity<HttpExceptionResponse> {
		val response = HttpExceptionResponse(exception.exceptionEnum)
		return ResponseEntity.status(response.status).body(response);
	}
}