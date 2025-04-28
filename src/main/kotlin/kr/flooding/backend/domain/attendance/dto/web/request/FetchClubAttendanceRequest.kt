package kr.flooding.backend.domain.attendance.dto.web.request

import kr.flooding.backend.domain.attendance.enums.AttendanceStatus
import java.time.LocalDate
import java.util.UUID

data class FetchClubAttendanceRequest(
	val clubId: UUID,
	val period: Int,
	val date: LocalDate,
	val status: AttendanceStatus?
)
