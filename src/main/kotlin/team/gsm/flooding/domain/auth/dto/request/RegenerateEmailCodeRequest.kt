package team.gsm.flooding.domain.auth.dto.request

import jakarta.validation.constraints.NotNull

data class RegenerateEmailCodeRequest(
	@field:NotNull
	val email: String,
)
