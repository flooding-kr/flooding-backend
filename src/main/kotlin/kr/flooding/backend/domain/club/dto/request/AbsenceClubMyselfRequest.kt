package kr.flooding.backend.domain.club.dto.request

import java.util.UUID

data class AbsenceClubMyselfRequest(
	val clubId: UUID,
	val reason: String,
	val period: Int,
)
