package kr.flooding.backend.domain.homebase.dto.response

import kr.flooding.backend.domain.classroom.entity.HomebaseTable
import kr.flooding.backend.domain.homebase.entity.HomebaseGroup
import kr.flooding.backend.domain.homebaseParticipants.entity.HomebaseParticipant
import java.time.LocalDate

class FetchReservedHomebaseResponse(
	val homebaseTableId: Long,
	val floor: Int,
	val tableNumber: Int,
	val attendedAt: LocalDate?,
	val isAttended: Boolean,
	val participants: List<kr.flooding.backend.domain.homebase.dto.response.HomebaseParticipantResponse>,
) {
	companion object {
		fun toDto(
			homebaseTable: HomebaseTable,
			homebaseGroup: HomebaseGroup?,
			homebaseParticipants: List<HomebaseParticipant>,
		): FetchReservedHomebaseResponse =
			FetchReservedHomebaseResponse(
				homebaseTableId = homebaseTable.id,
				floor = homebaseTable.homebase.floor,
				tableNumber = homebaseTable.tableNumber,
				attendedAt = homebaseGroup?.attendedAt,
				isAttended = homebaseGroup != null,
				participants =
					homebaseParticipants.orEmpty().map {
						kr.flooding.backend.domain.homebase.dto.response.HomebaseParticipantResponse
							.toDto(it)
					},
			)
	}
}
