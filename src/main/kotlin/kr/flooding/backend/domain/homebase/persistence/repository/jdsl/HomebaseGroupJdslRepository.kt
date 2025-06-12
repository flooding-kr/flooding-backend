package kr.flooding.backend.domain.homebase.persistence.repository.jdsl

import kr.flooding.backend.domain.homebase.persistence.entity.HomebaseGroup
import kr.flooding.backend.domain.user.persistence.entity.User
import java.time.LocalDate

interface HomebaseGroupJdslRepository {
	fun findWithHomebaseTableWithHomebaseAndProposerByProposerOrParticipantsAndAttendedAt(
		student: User,
		attendedAt: LocalDate,
	): List<HomebaseGroup>

	fun findWithParticipantsAndProposerByPeriodAndHomebaseTableHomebaseFloorAndAttendedAt(
		period: Int,
		floor: Int,
		attendedAt: LocalDate,
	): List<HomebaseGroup>

	fun existsByAttendedAtAndPeriodAndProposerInOrParticipantIn(
		attendedAt: LocalDate,
		period: Int,
		proposer: List<User>,
		participant: List<User>,
	): Boolean
}
