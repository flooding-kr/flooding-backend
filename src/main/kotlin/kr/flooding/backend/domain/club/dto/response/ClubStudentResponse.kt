package kr.flooding.backend.domain.club.dto.response

import kr.flooding.backend.domain.user.persistence.entity.StudentInfo
import kr.flooding.backend.domain.user.persistence.entity.User
import java.util.UUID

class ClubStudentResponse(
	val id: UUID?,
	val name: String,
	val studentInfo: StudentInfo,
	val profileImageUrl: String?,
) {
	companion object {
		fun toDto(user: User, profileImageUrl: String?): ClubStudentResponse {
			val studentInfo = requireNotNull(user.studentInfo)
			return ClubStudentResponse(
				id = user.id,
				name = user.name,
				studentInfo = studentInfo,
				profileImageUrl = profileImageUrl,
			)
		}
	}
}
