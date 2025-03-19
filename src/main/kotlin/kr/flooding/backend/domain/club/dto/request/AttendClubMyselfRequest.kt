package kr.flooding.backend.domain.club.dto.request

import java.util.UUID

data class AttendClubMyselfRequest(
	val clubId: UUID,
	val period: Int,
)
