package kr.flooding.backend.domain.club.dto.common.response

import kr.flooding.backend.domain.user.persistence.entity.User
import java.util.UUID

class ClubTeacherResponse(
	val id: UUID?,
	val name: String,
	val profileImageUrl: String?,
) {
	companion object {
		fun toDto(user: User, profileImageUrl: String?): ClubTeacherResponse {
			return ClubTeacherResponse(
				id = user.id,
				name = user.name,
				profileImageUrl = profileImageUrl,
			)
		}
	}
}
