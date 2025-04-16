package kr.flooding.backend.domain.homebase.dto.response

import kr.flooding.backend.domain.homebase.persistence.entity.HomebaseGroup
import kr.flooding.backend.domain.homebaseTable.persistence.entity.HomebaseTable
import kr.flooding.backend.domain.user.persistence.entity.User

class FetchReservedHomebaseResponse(
	val homebaseTableId: Long,
	val floor: Int,
	val tableNumber: Int,
	val isAttended: Boolean,
	val participants: List<HomebaseParticipantResponse>,
	val maxSeats: Int,
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
				maxSeats = homebaseTable.maxSeats,
				floor = homebaseTable.homebase.floor,
				tableNumber = homebaseTable.tableNumber,
				isAttended =
					(homebaseGroup?.participants?.any { it == currentUser } ?: false) ||
						(currentUser == homebaseGroup?.proposer),
				participants =
					listOfNotNull(proposerAsHomebaseParticipant) +
						homebaseGroup?.participants.orEmpty().map {
							HomebaseParticipantResponse.toDto(it)
						},
			)
		}
	}
}
