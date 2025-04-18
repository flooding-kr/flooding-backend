package kr.flooding.backend.domain.user.dto.web.request

import jakarta.validation.constraints.NotNull

data class WithdrawRequest(
	@field:NotNull
	val password: String,
)
