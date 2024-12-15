package com.example.demo.domain.user.dto.request

import jakarta.validation.constraints.NotNull

data class WithdrawRequest (
	@field:NotNull
	val password: String,
)