package com.example.demo.domain.auth.dto.response

data class SignInResponse (
	val accessToken: String,
	val refreshToken: String
)