package kr.flooding.backend.domain.attendance.dto.request

import java.util.UUID

data class AbsenceClubMyselfRequest(
	val clubId: UUID,
	val reason: String,
	val period: Int,
)
