package com.example.demo.domain.auth.dto.request

import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern

data class RegenerateEmailCodeRequest (
	@field:NotNull
	val email: String,
)