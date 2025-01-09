package team.gsm.flooding.domain.attendance.repository

import org.springframework.data.jpa.repository.JpaRepository
import team.gsm.flooding.domain.attendance.entity.Attendance
import team.gsm.flooding.domain.attendance.entity.AttendanceGroup
import team.gsm.flooding.domain.user.entity.User
import java.time.LocalDate
import java.util.UUID

interface AttendanceGroupRepository: JpaRepository<AttendanceGroup, UUID> {
	fun findByAttendedAtAndProposer(
		attendedAt: LocalDate,
		proposer: Attendance
	): AttendanceGroup
}