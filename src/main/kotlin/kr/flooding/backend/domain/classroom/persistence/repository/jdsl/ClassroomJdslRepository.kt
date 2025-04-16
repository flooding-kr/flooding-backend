package kr.flooding.backend.domain.classroom.persistence.repository.jdsl

import kr.flooding.backend.domain.classroom.persistence.entity.BuildingType
import kr.flooding.backend.domain.classroom.persistence.entity.Classroom

interface ClassroomJdslRepository {
	fun findWithTeacherByFloorAndBuildingTypeAndInName(
		floor: Int,
		buildingType: BuildingType,
		name: String,
	): List<Classroom>
}
