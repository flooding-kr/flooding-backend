package com.example.demo.domain.auth.dto.request

import jakarta.validation.constraints.NotNull

data class WithdrawRequest (
	@field:NotNull
	val password: String,
)