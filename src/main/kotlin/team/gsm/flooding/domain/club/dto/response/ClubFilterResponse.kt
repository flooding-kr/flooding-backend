package team.gsm.flooding.domain.club.dto.response

import team.gsm.flooding.domain.club.entity.Club
import team.gsm.flooding.domain.user.entity.User
import java.util.UUID

class ClubFilterResponse(
	val id: UUID?,
	val name: String,
	val thumbnailImageUrl: String?,
	val isLeader: Boolean,
) {
	companion object {
		fun toDto(
			club: Club,
			currentUser: User,
		): ClubFilterResponse =
			ClubFilterResponse(
				id = club.id,
				name = club.name,
				thumbnailImageUrl = club.thumbnailImageUrl,
				isLeader = club.leader == currentUser,
			)
	}
}
