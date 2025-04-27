package kr.flooding.backend.domain.attendance.dto.web.request

import java.util.UUID

data class AttendClubLeaderRequest(
	val clubId: UUID,
	val period: Int,
	val studentIds: List<UUID>,
)
