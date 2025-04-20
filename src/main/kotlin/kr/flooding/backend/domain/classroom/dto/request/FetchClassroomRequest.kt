package kr.flooding.backend.domain.classroom.dto.request

import kr.flooding.backend.domain.classroom.enums.BuildingType

class FetchClassroomRequest(
    val floor: Int,
    val buildingType: BuildingType,
    val search: String,
)
