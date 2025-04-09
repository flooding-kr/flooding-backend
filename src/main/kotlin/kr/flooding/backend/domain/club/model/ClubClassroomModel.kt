package kr.flooding.backend.domain.club.model

import kr.flooding.backend.domain.classroom.entity.BuildingType
import kr.flooding.backend.domain.classroom.entity.Classroom

class ClubClassroomModel(
	val id: Long,
	val floor: Int,
	val name: String,
	val buildingType: BuildingType,
) {
	companion object {
		fun toDto(classroom: Classroom): ClubClassroomModel =
			ClubClassroomModel(
				id = classroom.id,
				name = classroom.name,
				floor = classroom.floor,
				buildingType = classroom.buildingType,
			)
	}
}
