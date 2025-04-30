package kr.flooding.backend.domain.attendance.dto.common.response

import kr.flooding.backend.domain.attendance.enums.AttendanceStatus
import kr.flooding.backend.domain.file.shared.PresignedUrlModel

class FetchClubAttendanceResponse (
	val status: AttendanceStatus,
	val grade: Int,
	val classroom: Int,
	val number: Int,
	val name: String,
	val profileImage: PresignedUrlModel?
)