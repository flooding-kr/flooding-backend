package kr.flooding.backend.domain.homebase.dto.common.response

import kr.flooding.backend.domain.homebaseTable.persistence.entity.HomebaseTable

class HomebaseTableResponse(
	val id: Long,
	val floor: Int,
	val tableNumber: Int,
	val maxSeats: Int,
) {
	companion object {
		fun toDto(homebaseTable: HomebaseTable): HomebaseTableResponse =
			HomebaseTableResponse(
				id = homebaseTable.id,
				floor = homebaseTable.homebase.floor,
				tableNumber = homebaseTable.tableNumber,
				maxSeats = homebaseTable.maxSeats,
			)
	}
}
