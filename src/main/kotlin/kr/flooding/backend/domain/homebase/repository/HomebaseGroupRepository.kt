package kr.flooding.backend.domain.homebase.repository

import kr.flooding.backend.domain.attendance.entity.Attendance
import kr.flooding.backend.domain.classroom.entity.HomebaseTable
import kr.flooding.backend.domain.homebase.entity.HomebaseGroup
import kr.flooding.backend.domain.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.time.LocalDate
import java.util.UUID

interface HomebaseGroupRepository : JpaRepository<HomebaseGroup, UUID> {
	fun findByAttendedAtAndProposer(
		attendedAt: LocalDate,
		proposer: Attendance,
	): HomebaseGroup

	fun existsByHomebaseTableAndPeriodAndAttendedAt(
		homebaseTable: HomebaseTable,
		period: Int,
		attendedAt: LocalDate,
	): Boolean

    @Query("""
    SELECT hg
    FROM HomebaseGroup hg
	LEFT JOIN hg.participants p
    WHERE (hg.proposer.student = :student OR p.user = :student)
    AND hg.attendedAt = :attendedAt
	""")
	fun findByProposerStudentOrParticipantsAndAttendedAt(
		student: User,
		attendedAt: LocalDate,
	): List<HomebaseGroup>

	fun findByPeriodAndHomebaseTableHomebaseFloorAndAttendedAt(
		period: Int,
		floor: Int,
		attendedAt: LocalDate,
	): List<HomebaseGroup>
}
