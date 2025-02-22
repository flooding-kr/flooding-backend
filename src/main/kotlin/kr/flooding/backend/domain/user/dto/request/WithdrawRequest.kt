package kr.flooding.backend.domain.user.dto.request

import jakarta.validation.constraints.NotNull

data class WithdrawRequest(
	@field:NotNull
	val password: String,
)
