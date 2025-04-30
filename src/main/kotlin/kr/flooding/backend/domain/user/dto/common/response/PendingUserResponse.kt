package kr.flooding.backend.domain.user.dto.common.response

import kr.flooding.backend.domain.file.shared.PresignedUrlModel
import kr.flooding.backend.domain.user.shared.StudentInfoModel
import java.util.UUID

data class PendingUserResponse(
	val id: UUID,
	val name: String,
	val email: String,
	val studentInfoResponse: StudentInfoModel?,
	val teacherInfoResponse: TeacherInfoResponse?,
	val profileImage: PresignedUrlModel?,
)
