package com.example.demo.domain.auth.dto.response

import java.time.LocalDateTime
import java.util.Date

data class SignInResponse (
	val accessToken: String,
	val accessTokenExpiredAt: LocalDateTime,
	val refreshToken: String,
	val refreshTokenExpiredAt: LocalDateTime
)