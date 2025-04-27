package kr.flooding.backend.domain.attendance.dto.web.response

data class FetchAttendanceResponse(
	val reason: String?,
	val isChanged: Boolean,
	val isAttended: Boolean,
)
