package kr.flooding.backend.domain.classroom.dto.response

import kr.flooding.backend.domain.user.entity.User
import java.util.UUID

class ClassroomTeacherResponse(
	val id: UUID?,
	val name: String,
) {
	companion object {
		fun toDto(user: User): ClassroomTeacherResponse =
			ClassroomTeacherResponse(
				id = user.id,
				name = user.name,
			)
	}
}
