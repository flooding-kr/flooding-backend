package kr.flooding.backend.domain.club.dto.request

import java.util.UUID

data class UpdateClubRequest(
	val name: String?,

	val description: String?,

	val classroomId: Long?,

	val mainImageUrl: String?,

	val activityImageUrls: List<String>?,

	val clubId: UUID?,
)
