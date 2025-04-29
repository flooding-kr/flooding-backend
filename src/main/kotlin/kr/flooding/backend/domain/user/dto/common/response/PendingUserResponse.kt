package kr.flooding.backend.domain.user.dto.common.response

import java.util.UUID

data class PendingUserResponse(
	val id: UUID,
	val name: String,
	val email: String,
	val studentInfoResponse: StudentInfoResponse?,
	val teacherInfoResponse: TeacherInfoResponse?,
	val profileImageUrl: String?,
)
