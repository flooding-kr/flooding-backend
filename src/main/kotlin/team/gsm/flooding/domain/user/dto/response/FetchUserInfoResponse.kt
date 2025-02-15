package team.gsm.flooding.domain.user.dto.response

import team.gsm.flooding.domain.user.entity.Gender
import java.util.UUID

data class FetchUserInfoResponse(
	val id: UUID,
	val name: String,
	val email: String,
	val gender: Gender,
	val studentInfo: StudentInfoResponse,
)
