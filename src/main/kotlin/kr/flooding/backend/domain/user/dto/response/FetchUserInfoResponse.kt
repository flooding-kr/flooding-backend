package kr.flooding.backend.domain.user.dto.response

import kr.flooding.backend.domain.user.enums.Gender
import java.util.UUID

data class FetchUserInfoResponse(
	val id: UUID,
	val name: String,
	val email: String,
	val gender: Gender,
	val studentInfo: StudentInfoResponse?,
)
