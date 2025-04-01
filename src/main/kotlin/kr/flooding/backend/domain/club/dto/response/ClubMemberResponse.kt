package kr.flooding.backend.domain.club.dto.response

import kr.flooding.backend.domain.user.entity.StudentInfo
import kr.flooding.backend.domain.user.entity.User
import java.util.UUID

class ClubMemberResponse(
	val id: UUID?,
	val name: String,
	val studentInfo: StudentInfo,
) {
	companion object {
		fun toDto(user: User): ClubMemberResponse {
			val studentInfo = requireNotNull(user.studentInfo)
			return ClubMemberResponse(
				id = user.id,
				name = user.name,
				studentInfo = studentInfo,
			)
		}
	}
}
