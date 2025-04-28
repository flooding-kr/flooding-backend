package kr.flooding.backend.domain.attendance.dto.common.response

import kr.flooding.backend.domain.attendance.enums.AttendanceStatus

class FetchClubAttendanceResponse (
	val status: AttendanceStatus,
	val grade: Int,
	val classroom: Int,
	val number: Int,
	val name: String,
	val profileImageUrl: String?
)