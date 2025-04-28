package kr.flooding.backend.domain.attendance.dto.web.request

import jakarta.validation.constraints.NotBlank
import java.util.UUID

data class AbsenceClubLeaderRequest(
	val clubId: UUID,
	val period: Int,
	@field:NotBlank
	val reason: String,
	val studentIds: List<UUID>,
)
