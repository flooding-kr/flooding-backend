package kr.flooding.backend.domain.homebase.repository.jdsl

import kr.flooding.backend.domain.homebase.entity.HomebaseGroup
import kr.flooding.backend.domain.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.time.LocalDate
import java.util.UUID

interface HomebaseGroupJdslRepository : JpaRepository<HomebaseGroup, UUID> {
	@Query(
		"""
			SELECT hg
    		FROM HomebaseGroup hg
			LEFT JOIN hg.participants p
			JOIN FETCH hg.homebaseTable
			JOIN FETCH hg.homebaseTable.homebase
			JOIN FETCH hg.proposer
    		WHERE (hg.proposer = :student OR p.user = :student)
    		AND hg.attendedAt = :attendedAt
	""",
	)
	fun findWithHomebaseTableWithHomebaseAndProposerByProposerOrParticipantsAndAttendedAt(
		student: User,
		attendedAt: LocalDate,
	): List<HomebaseGroup>

	@Query(
		"""
			SELECT hg
			FROM HomebaseGroup hg
			JOIN FETCH hg.proposer
			WHERE hg.period = :period
			AND hg.homebaseTable.homebase.floor = :floor
			AND hg.attendedAt = :attendedAt
	""",
	)
	fun findWithParticipantsAndProposerByPeriodAndHomebaseTableHomebaseFloorAndAttendedAt(
		period: Int,
		floor: Int,
		attendedAt: LocalDate,
	): List<HomebaseGroup>
}
