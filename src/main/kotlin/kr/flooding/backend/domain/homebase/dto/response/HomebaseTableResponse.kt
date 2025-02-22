package kr.flooding.backend.domain.homebase.dto.response

import kr.flooding.backend.domain.classroom.entity.HomebaseTable

class HomebaseTableResponse(
	val id: Long,
	val floor: Int,
	val tableNumber: Int,
) {
	companion object {
		fun toDto(homebaseTable: HomebaseTable): HomebaseTableResponse =
			HomebaseTableResponse(
				id = homebaseTable.id,
				floor = homebaseTable.homebase.floor,
				tableNumber = homebaseTable.tableNumber,
			)
	}
}
