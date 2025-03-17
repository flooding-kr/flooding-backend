package kr.flooding.backend.domain.club.dto.request

import java.util.UUID

data class AbsenceClubLeaderRequest(
	val clubId: UUID,
	val period: Int,
	val reason: String,
	val studentId: UUID,
)
