package team.gsm.flooding.domain.user.dto.response

import team.gsm.flooding.domain.user.entity.Gender
import team.gsm.flooding.domain.user.entity.Role
import team.gsm.flooding.domain.user.entity.StudentInfo

data class FetchUserInfoResponse (
	val name: String,
	val email: String,
	val isVerified: Boolean,
	val studentInfo: StudentInfoResponse,
	val gender: Gender,
	val roles: List<Role>
)