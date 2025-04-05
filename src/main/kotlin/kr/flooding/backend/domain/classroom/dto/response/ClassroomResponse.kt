package kr.flooding.backend.domain.classroom.dto.response

import kr.flooding.backend.domain.classroom.entity.Classroom

class ClassroomResponse(
	val id: Long,
	val floor: Int,
	val name: String,
	val description: String,
	val isHomebase: Boolean,
	val teacher: ClassroomTeacherResponse,
) {
	companion object {
		fun toDto(classroom: Classroom) =
			ClassroomResponse(
				id = classroom.id,
				floor = classroom.floor,
				name = classroom.name,
				description = classroom.description,
				isHomebase = classroom.isHomebase,
				teacher = ClassroomTeacherResponse.toDto(classroom.teacher),
			)
	}
}
