package kr.flooding.backend.domain.club.dto.common.response

import kr.flooding.backend.domain.file.shared.PresignedUrlModel
import kr.flooding.backend.domain.user.persistence.entity.User
import java.util.UUID

class ClubTeacherResponse(
	val id: UUID?,
	val name: String,
	val profileImage: PresignedUrlModel?,
) {
	companion object {
		fun toDto(user: User, profileImage: PresignedUrlModel?): ClubTeacherResponse {
			return ClubTeacherResponse(
				id = user.id,
				name = user.name,
				profileImage = profileImage,
			)
		}
	}
}
