package com.example.demo.domain.auth.dto.request

import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern

data class VerifyEmailRequest (
	@field:NotNull
	val email: String,

	@field:NotNull
	val password: String,
)