package kr.flooding.backend.domain.attendance.persistence.repository.jdsl

import kr.flooding.backend.domain.attendance.persistence.entity.Attendance
import java.time.LocalDate
import java.util.*

interface AttendanceJdslRepository {
	fun findWithStudentAndClubByAttendedAtAndPeriodAndClubId(
		attendedAt: LocalDate,
		period: Int,
		clubId: UUID,
	): List<Attendance>
}