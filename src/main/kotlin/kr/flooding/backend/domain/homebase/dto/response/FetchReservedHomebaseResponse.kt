package kr.flooding.backend.domain.homebase.dto.response

import kr.flooding.backend.domain.classroom.entity.HomebaseTable
import kr.flooding.backend.domain.homebase.entity.HomebaseGroup
import java.time.LocalDate

class FetchReservedHomebaseResponse(
	val homebaseTableId: Long,
	val floor: Int,
	val tableNumber: Int,
	val attendedAt: LocalDate?,
	val isAttended: Boolean,
	val participants: List<kr.flooding.backend.domain.homebase.dto.response.AttendanceResponse>,
) {
	companion object {
		fun toDto(
			homebaseTable: HomebaseTable,
			homebaseGroup: HomebaseGroup?,
		): FetchReservedHomebaseResponse =
			FetchReservedHomebaseResponse(
				homebaseTableId = homebaseTable.id,
				floor = homebaseTable.homebase.floor,
				tableNumber = homebaseTable.tableNumber,
				attendedAt = homebaseGroup?.attendedAt,
				isAttended = homebaseGroup != null,
				participants =
					homebaseGroup?.participants.orEmpty().map {
						kr.flooding.backend.domain.homebase.dto.response.AttendanceResponse
							.toDto(it)
					},
			)
	}
}
