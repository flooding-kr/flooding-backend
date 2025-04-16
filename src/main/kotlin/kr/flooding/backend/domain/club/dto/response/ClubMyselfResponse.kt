package kr.flooding.backend.domain.club.dto.response

import kr.flooding.backend.domain.club.persistence.entity.Club
import kr.flooding.backend.domain.club.persistence.entity.ClubStatus
import kr.flooding.backend.domain.club.persistence.entity.ClubType
import kr.flooding.backend.domain.user.persistence.entity.User
import java.util.UUID

class ClubMyselfResponse(
	val id: UUID?,
	val name: String,
	val thumbnailImageUrl: String?,
	val isLeader: Boolean,
	val isRecruiting: Boolean,
	val status: ClubStatus,
	val type: ClubType,
) {
	companion object {
		fun toDto(
			club: Club,
			currentUser: User,
		): ClubMyselfResponse =
			ClubMyselfResponse(
				id = club.id,
				name = club.name,
				thumbnailImageUrl = club.thumbnailImageUrl,
				isLeader = club.leader == currentUser,
				status = club.status,
				type = club.type,
				isRecruiting = club.isRecruiting,
			)
	}
}
