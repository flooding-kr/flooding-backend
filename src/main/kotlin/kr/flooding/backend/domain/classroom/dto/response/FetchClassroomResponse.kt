package kr.flooding.backend.domain.classroom.dto.response

import kr.flooding.backend.domain.classroom.entity.Classroom

class FetchClassroomResponse(
	val classrooms: List<ClassroomResponse>,
) {
	companion object {
		fun toDto(classrooms: List<Classroom>): FetchClassroomResponse =
			FetchClassroomResponse(classrooms.map { ClassroomResponse.toDto(it) })
	}
}
