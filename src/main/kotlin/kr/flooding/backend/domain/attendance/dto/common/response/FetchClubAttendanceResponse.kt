package kr.flooding.backend.domain.attendance.dto.common.response

import kr.flooding.backend.domain.attendance.enums.AttendanceStatus
import kr.flooding.backend.domain.file.shared.PresignedUrlModel
import java.util.UUID

class FetchClubAttendanceResponse (
	val userId: UUID,
	val status: AttendanceStatus,
	val grade: Int,
	val classroom: Int,
	val number: Int,
	val name: String,
	val profileImage: PresignedUrlModel?
)