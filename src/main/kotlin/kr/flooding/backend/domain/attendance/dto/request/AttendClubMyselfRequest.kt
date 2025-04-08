package kr.flooding.backend.domain.attendance.dto.request

import java.util.UUID

data class AttendClubMyselfRequest(
	val clubId: UUID,
	val period: Int,
)
