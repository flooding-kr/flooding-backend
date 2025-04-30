package kr.flooding.backend.domain.club.dto.common.response

import kr.flooding.backend.domain.club.persistence.entity.Club
import kr.flooding.backend.domain.club.enums.ClubStatus
import kr.flooding.backend.domain.club.enums.ClubType
import kr.flooding.backend.domain.file.shared.PresignedUrlModel
import kr.flooding.backend.domain.user.persistence.entity.User
import java.util.UUID

class ClubMyselfResponse(
    val id: UUID?,
    val name: String,
    val thumbnailImage: PresignedUrlModel?,
    val isLeader: Boolean,
    val isRecruiting: Boolean,
    val status: ClubStatus,
    val type: ClubType,
) {
	companion object {
		fun toDto(
			club: Club,
			currentUser: User,
			thumbnailImage: PresignedUrlModel?,
		): ClubMyselfResponse =
			ClubMyselfResponse(
				id = club.id,
				name = club.name,
				thumbnailImage = thumbnailImage,
				isLeader = club.leader == currentUser,
				status = club.status,
				type = club.type,
				isRecruiting = club.isRecruiting,
			)
	}
}
