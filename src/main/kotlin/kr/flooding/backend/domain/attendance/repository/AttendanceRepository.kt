package kr.flooding.backend.domain.attendance.repository

import kr.flooding.backend.domain.attendance.entity.Attendance
import kr.flooding.backend.domain.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDate
import java.util.Optional
import java.util.UUID

interface AttendanceRepository : JpaRepository<Attendance, UUID> {
	fun existsByAttendedAtAndPeriodAndStudentIn(
		attendedAt: LocalDate,
		period: Int,
		students: List<User>,
	): Boolean

	fun findByAttendedAtAndStudent(
		attendedAt: LocalDate,
		student: User,
	): Optional<Attendance>
}
