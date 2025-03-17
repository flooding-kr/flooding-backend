package kr.flooding.backend.domain.homebase.repository.jpa

import kr.flooding.backend.domain.homebase.entity.HomebaseGroup
import kr.flooding.backend.domain.homebaseTable.entity.HomebaseTable
import kr.flooding.backend.domain.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDate
import java.util.UUID

interface HomebaseGroupRepository : JpaRepository<HomebaseGroup, UUID> {
	fun findByAttendedAtAndProposer(
		attendedAt: LocalDate,
		proposer: User,
	): HomebaseGroup

	fun existsByHomebaseTableAndPeriodAndAttendedAt(
		homebaseTable: HomebaseTable,
		period: Int,
		attendedAt: LocalDate,
	): Boolean
}
