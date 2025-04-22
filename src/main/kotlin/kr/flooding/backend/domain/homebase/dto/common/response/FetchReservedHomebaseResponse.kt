package kr.flooding.backend.domain.homebase.dto.common.response

import kr.flooding.backend.domain.homebase.persistence.entity.HomebaseGroup
import kr.flooding.backend.domain.homebaseTable.persistence.entity.HomebaseTable
import kr.flooding.backend.domain.user.persistence.entity.User
import java.util.UUID

class FetchReservedHomebaseResponse(
	val homebaseTable: HomebaseTableResponse,
	val isAttended: Boolean,
	val participants: List<HomebaseParticipantResponse>,
	val reason: String?,
	val homebaseGroupId: UUID?,
) {
	companion object {
		fun toDto(
			homebaseTable: HomebaseTable,
			homebaseGroup: HomebaseGroup?,
			currentUser: User,
		): FetchReservedHomebaseResponse {
			val proposerAsParticipants =
				homebaseGroup?.proposer?.let {
					listOf(HomebaseParticipantResponse.fromUser(it))
				} ?: emptyList()

			val participants = homebaseGroup?.participants.orEmpty().map {
				HomebaseParticipantResponse.toDto(it)
			}

			return FetchReservedHomebaseResponse(
				homebaseTable = HomebaseTableResponse.toDto(homebaseTable),
				reason = homebaseGroup?.reason,
				homebaseGroupId = homebaseGroup?.id,
				isAttended =
					(homebaseGroup?.participants?.any { it == currentUser } ?: false) ||
						(currentUser == homebaseGroup?.proposer),
				participants = participants + proposerAsParticipants,
			)
		}
	}
}
