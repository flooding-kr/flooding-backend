package kr.flooding.backend.domain.homebase.dto.common.response

import kr.flooding.backend.domain.homebase.persistence.entity.HomebaseGroup
import kr.flooding.backend.domain.user.persistence.entity.User

class FetchMyReservedHomebaseResponse(
	val homebaseTable: HomebaseTableResponse,
	val period: Int,
	val isProposer: Boolean,
	val participants: List<HomebaseParticipantResponse>,
	val reason: String,
	val maxSeats: Int,
) {
	companion object {
		fun toDto(
			homebaseGroup: HomebaseGroup,
			currentUser: User,
		): FetchMyReservedHomebaseResponse {
			val proposerAsHomebaseParticipant = HomebaseParticipantResponse.fromUser(homebaseGroup.proposer)

			return FetchMyReservedHomebaseResponse(
				homebaseTable = HomebaseTableResponse.toDto(homebaseGroup.homebaseTable),
				period = homebaseGroup.period,
				participants =
					listOf(proposerAsHomebaseParticipant) +
						homebaseGroup.participants.map {
							HomebaseParticipantResponse.toDto(it)
						},
				isProposer = homebaseGroup.proposer == currentUser,
				maxSeats = homebaseGroup.homebaseTable.maxSeats,
				reason = homebaseGroup.reason,
			)
		}
	}
}
