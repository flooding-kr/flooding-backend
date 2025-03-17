package kr.flooding.backend.domain.club.dto.request

import java.util.UUID

data class AbsenceClubLeaderRequest(
	val clubId: UUID,
	val reason: String,
	val period: Int,
	val studentId: UUID,
)
