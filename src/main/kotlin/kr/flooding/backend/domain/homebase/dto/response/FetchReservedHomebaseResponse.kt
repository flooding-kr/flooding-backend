package kr.flooding.backend.domain.homebase.dto.response

import kr.flooding.backend.domain.classroom.entity.HomebaseTable
import kr.flooding.backend.domain.homebase.entity.HomebaseGroup
import kr.flooding.backend.domain.user.entity.User

class FetchReservedHomebaseResponse(
	val homebaseTableId: Long,
	val floor: Int,
	val tableNumber: Int,
	val isAttended: Boolean,
	val participants: List<kr.flooding.backend.domain.homebase.dto.response.HomebaseParticipantResponse>,
) {
	companion object {
		fun toDto(
			homebaseTable: HomebaseTable,
			homebaseGroup: HomebaseGroup?,
			currentUser: User,
		): FetchReservedHomebaseResponse {
			val proposerAsHomebaseParticipant =
				homebaseGroup?.proposer?.let {
					HomebaseParticipantResponse.fromUser(it)
				}

			return FetchReservedHomebaseResponse(
				homebaseTableId = homebaseTable.id,
				floor = homebaseTable.homebase.floor,
				tableNumber = homebaseTable.tableNumber,
				isAttended =
					(homebaseGroup?.participants?.any { it == currentUser } ?: false) ||
						(currentUser == homebaseGroup?.proposer),
				participants =
					listOfNotNull(proposerAsHomebaseParticipant) +
						homebaseGroup?.participants.orEmpty().map {
							kr.flooding.backend.domain.homebase.dto.response.HomebaseParticipantResponse
								.toDto(it)
						},
			)
		}
	}
}
