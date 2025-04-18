package kr.flooding.backend.domain.user.dto.web.response

import kr.flooding.backend.domain.user.enums.Gender
import kr.flooding.backend.domain.user.dto.common.response.StudentInfoResponse
import java.util.UUID

data class FetchPendingUserListResponse(
	val users: List<FetchUserInfoResponse>
)
