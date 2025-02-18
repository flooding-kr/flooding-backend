package team.gsm.flooding.domain.homebase.dto.response

import team.gsm.flooding.domain.homebase.entity.HomebaseGroup
import team.gsm.flooding.domain.user.entity.User
import java.time.LocalDate
import java.util.UUID

class FindMyReservedHomebaseResponse(
	val homebaseGroupId: UUID?,
	val homebaseTable: HomebaseTableResponse,
	val attendedAt: LocalDate?,
	val isProposer: Boolean,
	val participants: List<AttendanceResponse>,
) {
	companion object {
		fun toDto(
			homebaseGroup: HomebaseGroup,
			currentUser: User,
		): FindMyReservedHomebaseResponse =
			FindMyReservedHomebaseResponse(
				homebaseGroupId = homebaseGroup.id,
				homebaseTable = HomebaseTableResponse.toDto(homebaseGroup.homebaseTable),
				attendedAt = homebaseGroup.attendedAt,
				participants = homebaseGroup.participants.map { AttendanceResponse.toDto(it) },
				isProposer = homebaseGroup.proposer.student == currentUser,
			)
	}
}
