package team.gsm.flooding.domain.club.dto.request

import team.gsm.flooding.domain.club.entity.ClubType

class CreateClubRequest(
	val name: String,

	val description: String,

	val type: ClubType,

	val classroomId: Long,

	val mainImageUrl: String?,

	val activityImageUrls: List<String>,
)
