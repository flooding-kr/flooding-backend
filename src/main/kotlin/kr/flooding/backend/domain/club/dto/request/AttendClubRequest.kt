package kr.flooding.backend.domain.club.dto.request

import java.util.UUID

data class AttendClubRequest(
	val clubId: UUID,
	val period: Int,
)
