package kr.flooding.backend.domain.auth.dto.request

import jakarta.validation.constraints.NotNull

data class ReissueEmailCodeRequest(
	@field:NotNull
	val email: String,
)
