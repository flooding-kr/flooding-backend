package team.gsm.flooding.domain.attendance.repository

import org.springframework.data.jpa.repository.JpaRepository
import team.gsm.flooding.domain.attendance.entity.Attendance
import team.gsm.flooding.domain.attendance.entity.HomebaseGroup
import java.time.LocalDate
import java.util.UUID

interface HomebaseGroupRepository: JpaRepository<HomebaseGroup, UUID> {
	fun findByAttendedAtAndProposer(
		attendedAt: LocalDate,
		proposer: Attendance
	): HomebaseGroup
}