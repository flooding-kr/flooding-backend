package kr.flooding.backend.domain.user.dto.web.response

import kr.flooding.backend.domain.file.shared.PresignedUrlModel
import kr.flooding.backend.domain.role.enums.RoleType
import kr.flooding.backend.domain.user.enums.Gender
import kr.flooding.backend.domain.user.dto.common.response.TeacherInfoResponse
import kr.flooding.backend.domain.user.shared.StudentInfoModel
import java.util.UUID

data class FetchUserMyselfResponse(
	val id: UUID,
	val name: String,
	val email: String,
	val gender: Gender,
	val profileImage: PresignedUrlModel?,
	val studentInfo: StudentInfoModel?,
	val teacherInfo: TeacherInfoResponse?,
	val roles: List<RoleType>
)
