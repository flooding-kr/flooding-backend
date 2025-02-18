package team.gsm.flooding.domain.homebase.dto.response

import team.gsm.flooding.domain.classroom.entity.HomebaseTable

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
