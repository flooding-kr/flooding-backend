package kr.flooding.backend.domain.attendance.dto.web.request

import java.util.UUID

data class AttendClubMyselfRequest(
	val clubId: UUID,
	val period: Int,
)
