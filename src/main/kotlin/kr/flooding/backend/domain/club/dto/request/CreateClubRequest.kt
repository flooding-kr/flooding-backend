package kr.flooding.backend.domain.club.dto.request

import kr.flooding.backend.domain.club.persistence.entity.ClubType

class CreateClubRequest(
	val name: String,

	val description: String,

	val type: ClubType,

	val classroomId: Long,

	val thumbnailImageKey: String?,

	val activityImageKeys: List<String>,
)
