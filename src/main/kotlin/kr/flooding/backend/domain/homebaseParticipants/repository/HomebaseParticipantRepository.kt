package kr.flooding.backend.domain.homebaseParticipants.repository

import kr.flooding.backend.domain.homebase.entity.HomebaseGroup
import kr.flooding.backend.domain.homebaseParticipants.entity.HomebaseParticipant
import kr.flooding.backend.domain.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDate
import java.util.UUID

interface HomebaseParticipantRepository : JpaRepository<HomebaseParticipant, UUID> {
	fun findByHomebaseGroup(homebaseGroup: HomebaseGroup?): List<HomebaseParticipant>

	fun existsByHomebaseGroupAttendedAtAndHomebaseGroupPeriodAndHomebaseGroupProposerInAndUserIn(
		attendedAt: LocalDate,
		period: Int,
		proposer: List<User>,
		user: List<User>,
	): Boolean
}
