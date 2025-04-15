package kr.flooding.backend.domain.homebaseParticipants.persistence.jpa

import kr.flooding.backend.domain.homebase.persistence.entity.HomebaseGroup
import kr.flooding.backend.domain.homebaseParticipants.persistence.entity.HomebaseParticipant
import kr.flooding.backend.domain.user.persistence.entity.User
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
