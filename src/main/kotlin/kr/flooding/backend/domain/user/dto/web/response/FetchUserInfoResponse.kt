package kr.flooding.backend.domain.user.dto.web.response

import kr.flooding.backend.domain.user.enums.Gender
import kr.flooding.backend.domain.user.dto.common.response.StudentInfoResponse
import kr.flooding.backend.domain.user.dto.common.response.TeacherInfoResponse
import kr.flooding.backend.domain.user.persistence.entity.TeacherInfo
import java.util.UUID

data class FetchUserInfoResponse(
	val id: UUID,
	val name: String,
	val email: String,
	val gender: Gender,
	val profileImageUrl: String?,
	val studentInfo: StudentInfoResponse? = null,
	val teacherInfo: TeacherInfoResponse? = null,
)
