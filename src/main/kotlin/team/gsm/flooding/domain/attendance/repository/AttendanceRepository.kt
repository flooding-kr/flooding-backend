package team.gsm.flooding.domain.attendance.repository

import org.springframework.data.jpa.repository.JpaRepository
import team.gsm.flooding.domain.attendance.entity.Attendance
import team.gsm.flooding.domain.user.entity.User
import java.time.LocalDate
import java.util.Optional
import java.util.UUID

interface AttendanceRepository: JpaRepository<Attendance, UUID> {
	fun existsByAttendedAtAndPeriodAndStudentIn(
		attendedAt: LocalDate,
		period: Int,
		students: List<User>
	): Boolean

	fun findByAttendedAtAndStudent(
		attendedAt: LocalDate,
		student: User
	): Optional<Attendance>
}