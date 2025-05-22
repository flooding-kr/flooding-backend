package kr.flooding.backend.domain.homebase.persistence.repository.jpa

import kr.flooding.backend.domain.homebase.persistence.entity.HomebaseGroup
import kr.flooding.backend.domain.homebaseTable.persistence.entity.HomebaseTable
import kr.flooding.backend.domain.user.persistence.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDate
import java.util.UUID

interface HomebaseGroupRepository : JpaRepository<HomebaseGroup, UUID> {
	fun existsByHomebaseTableAndPeriodAndAttendedAt(
		homebaseTable: HomebaseTable,
		period: Int,
		attendedAt: LocalDate,
	): Boolean
}
