package kr.flooding.backend.domain.homebase.dto.response

import kr.flooding.backend.domain.homebase.entity.HomebaseGroup
import kr.flooding.backend.domain.user.entity.User
import java.time.LocalDate
import java.util.UUID

class FetchMyReservedHomebaseResponse(
	val homebaseGroupId: UUID?,
	val homebaseTable: HomebaseTableResponse,
	val attendedAt: LocalDate?,
	val isProposer: Boolean,
	val participants: List<kr.flooding.backend.domain.homebase.dto.response.HomebaseParticipantResponse>,
) {
	companion object {
		fun toDto(
			homebaseGroup: HomebaseGroup,
			currentUser: User,
		): FetchMyReservedHomebaseResponse {
			val proposerAsHomebaseParticipant = HomebaseParticipantResponse.fromUser(homebaseGroup.proposer)

			return FetchMyReservedHomebaseResponse(
				homebaseGroupId = homebaseGroup.id,
				homebaseTable = HomebaseTableResponse.toDto(homebaseGroup.homebaseTable),
				attendedAt = homebaseGroup.attendedAt,
				participants =
					listOf(proposerAsHomebaseParticipant) +
						homebaseGroup.participants.map {
							kr.flooding.backend.domain.homebase.dto.response.HomebaseParticipantResponse
								.toDto(it)
						},
				isProposer = homebaseGroup.proposer == currentUser,
			)
		}
	}
}
