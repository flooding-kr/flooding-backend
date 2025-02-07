package team.gsm.flooding.domain.user.dto.response

import team.gsm.flooding.domain.user.entity.Gender
import team.gsm.flooding.domain.user.entity.Role
import team.gsm.flooding.domain.user.entity.StudentInfo
import java.util.UUID

data class FetchUserInfoResponse (
	val id: UUID,
	val name: String,
	val email: String,
	val studentInfo: StudentInfoResponse,
	val gender: Gender,
)