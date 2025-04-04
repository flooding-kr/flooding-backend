package kr.flooding.backend.domain.classroom.repository.jdsl

import kr.flooding.backend.domain.classroom.entity.BuildingType
import kr.flooding.backend.domain.classroom.entity.Classroom

interface ClassroomJdslRepository {
	fun findWithTeacherByFloorAndBuildingTypeAndInName(
		floor: Int,
		buildingType: BuildingType,
		name: String,
	): List<Classroom>
}
