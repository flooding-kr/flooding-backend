package kr.flooding.backend.domain.attendance.dto.web.request

import java.util.UUID

data class AbsenceClubMyselfRequest(
	val clubId: UUID,
	val reason: String,
	val period: Int,
)
