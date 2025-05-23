package kr.flooding.backend.domain.club.dto.web.request

import java.util.UUID

data class UpdateClubRequest(
	val name: String?,

	val description: String?,

	val classroomId: Long?,

	val thumbnailImageKey: String?,

	val activityImageKeys: List<String>?,

	val clubId: UUID?,
)
