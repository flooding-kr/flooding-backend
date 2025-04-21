package kr.flooding.backend.domain.club.dto.common.response

import kr.flooding.backend.domain.classroom.enums.BuildingType
import kr.flooding.backend.domain.classroom.persistence.entity.Classroom

class ClubClassroomResponse(
	val id: Long,
	val floor: Int,
	val name: String,
	val buildingType: BuildingType,
) {
	companion object {
		fun toDto(classroom: Classroom): ClubClassroomResponse =
			ClubClassroomResponse(
				id = classroom.id,
				name = classroom.name,
				floor = classroom.floor,
				buildingType = classroom.buildingType,
			)
	}
}
