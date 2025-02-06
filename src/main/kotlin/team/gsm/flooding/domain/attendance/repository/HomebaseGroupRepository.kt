package team.gsm.flooding.domain.attendance.repository

import org.springframework.data.jpa.repository.JpaRepository
import team.gsm.flooding.domain.attendance.entity.Attendance
import team.gsm.flooding.domain.attendance.entity.HomebaseGroup
import team.gsm.flooding.domain.classroom.entity.HomebaseTable
import team.gsm.flooding.domain.user.entity.User
import java.time.LocalDate
import java.util.UUID

interface HomebaseGroupRepository: JpaRepository<HomebaseGroup, UUID> {
	fun findByAttendedAtAndProposer(
		attendedAt: LocalDate,
		proposer: Attendance
	): HomebaseGroup

	fun existsByHomebaseTableAndPeriodAndAttendedAt(homebaseTable: HomebaseTable, period: Int, attendedAt: LocalDate): Boolean
	fun findByProposerStudent(student: User): List<HomebaseGroup>
}