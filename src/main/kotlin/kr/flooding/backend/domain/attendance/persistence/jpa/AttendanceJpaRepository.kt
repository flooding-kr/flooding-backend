package kr.flooding.backend.domain.attendance.persistence.jpa

import kr.flooding.backend.domain.attendance.persistence.entity.Attendance
import kr.flooding.backend.domain.club.persistence.entity.Club
import kr.flooding.backend.domain.user.persistence.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDate
import java.util.Optional
import java.util.UUID

interface AttendanceJpaRepository : JpaRepository<Attendance, UUID> {
	fun existsByAttendedAtAndPeriodAndStudentIn(
		attendedAt: LocalDate,
		period: Int,
		students: List<User>,
	): Boolean

	fun findByAttendedAtAndPeriodAndStudent(
		attendedAt: LocalDate,
		period: Int,
		student: User,
	): Optional<Attendance>

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
