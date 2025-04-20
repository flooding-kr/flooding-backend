package kr.flooding.backend.domain.club.dto.web.request

import kr.flooding.backend.domain.club.enums.ClubType

class CreateClubRequest(
	val name: String,

	val description: String,

	val type: ClubType,

	val classroomId: Long,

	val thumbnailImageKey: String?,

	val activityImageKeys: List<String>,
)
