package kr.flooding.backend.domain.club.dto.request

import java.util.UUID

data class AttendClubLeaderRequest(
	val clubId: UUID,
	val period: Int,
	val studentIds: List<UUID>,
)
