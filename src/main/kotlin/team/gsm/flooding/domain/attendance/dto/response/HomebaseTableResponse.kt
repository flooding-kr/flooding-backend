package team.gsm.flooding.domain.attendance.dto.response

import team.gsm.flooding.domain.classroom.entity.HomebaseTable

class HomebaseTableResponse(
        val homebaseId: Long,
        val floor: Int,
        val name: String,
        val description: String,
        val teacher: UserResponse,
        val tableNumber: Int,
    ) {
        companion object {
            fun toDto(homebaseTable: HomebaseTable): HomebaseTableResponse {
                return HomebaseTableResponse(
                    homebaseId = homebaseTable.id,
                    floor = homebaseTable.homebase.floor,
                    name = homebaseTable.homebase.name,
                    description = homebaseTable.homebase.description,
                    teacher = UserResponse.toDto(homebaseTable.homebase.teacher),
                    tableNumber = homebaseTable.tableNumber,
                )
            }
        }
    }