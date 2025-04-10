package kr.flooding.backend.domain.club.dto.response

import kr.flooding.backend.domain.club.entity.Club
import kr.flooding.backend.domain.club.entity.ClubType
import kr.flooding.backend.domain.user.entity.User
import java.util.UUID

class ClubFilterResponse(
	val id: UUID?,
	val name: String,
	val thumbnailImageUrl: String?,
	val isLeader: Boolean,
	val isRecruiting: Boolean,
	val type: ClubType,
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
				type = club.type,
				isRecruiting = club.isRecruiting,
			)
	}
}
