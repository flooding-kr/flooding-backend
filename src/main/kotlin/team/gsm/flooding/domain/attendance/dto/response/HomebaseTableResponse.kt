package team.gsm.flooding.domain.attendance.dto.response

import team.gsm.flooding.domain.classroom.entity.HomebaseTable

class HomebaseTableResponse(
        val id: Long,
        val floor: Int,
        val tableNumber: Int,
    ) {
        companion object {
            fun toDto(homebaseTable: HomebaseTable): HomebaseTableResponse {
                return HomebaseTableResponse(
                    id = homebaseTable.id,
                    floor = homebaseTable.homebase.floor,
                    tableNumber = homebaseTable.tableNumber,
                )
            }
        }
    }