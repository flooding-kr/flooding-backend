package kr.flooding.backend.domain.club.dto.request

import kr.flooding.backend.domain.club.entity.ClubType

class CreateClubRequest(
	val name: String,

	val description: String,

	val type: ClubType,

	val classroomId: Long,

	val mainImageUrl: String?,

	val activityImageUrls: List<String>,
)
