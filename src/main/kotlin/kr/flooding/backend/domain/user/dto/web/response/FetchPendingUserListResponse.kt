package kr.flooding.backend.domain.user.dto.web.response

import kr.flooding.backend.domain.user.dto.common.response.PendingUserResponse

data class FetchPendingUserListResponse(
	val users: List<PendingUserResponse>
)
