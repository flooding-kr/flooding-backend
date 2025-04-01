package kr.flooding.backend.domain.attendance.repository

import kr.flooding.backend.domain.attendance.entity.Attendance
import kr.flooding.backend.domain.club.entity.Club
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

	fun findByStudentAndClubAndPeriodAndAttendedAt(
		student: User,
		club: Club?,
		period: Int,
		attendAt: LocalDate,
	): Optional<Attendance>

	fun existsByStudentAndClub(
		student: User,
		club: Club,
	): Boolean

	fun findByAttendedAtAndStudent(
		attendedAt: LocalDate,
		student: User,
	): Optional<Attendance>

	fun findByClubAndPeriodAndAttendedAt(
		club: Club,
		period: Int,
		attendedAt: LocalDate,
	): List<Attendance>
}
