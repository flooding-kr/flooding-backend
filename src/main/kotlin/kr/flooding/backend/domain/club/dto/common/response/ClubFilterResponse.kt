package kr.flooding.backend.domain.club.dto.common.response

import kr.flooding.backend.domain.club.persistence.entity.Club
import kr.flooding.backend.domain.club.enums.ClubType
import kr.flooding.backend.domain.file.shared.PresignedUrlModel
import kr.flooding.backend.domain.user.persistence.entity.User
import java.util.UUID

class ClubFilterResponse(
    val id: UUID?,
    val name: String,
    val thumbnailImage: PresignedUrlModel?,
    val isLeader: Boolean,
    val isRecruiting: Boolean,
    val type: ClubType,
) {
	companion object {
		fun toDto(
			club: Club,
			currentUser: User,
			thumbnailImage: PresignedUrlModel?,
		): ClubFilterResponse =
			ClubFilterResponse(
				id = club.id,
				name = club.name,
				thumbnailImage = thumbnailImage,
				isLeader = club.leader == currentUser,
				type = club.type,
				isRecruiting = club.isRecruiting,
			)
	}
}
