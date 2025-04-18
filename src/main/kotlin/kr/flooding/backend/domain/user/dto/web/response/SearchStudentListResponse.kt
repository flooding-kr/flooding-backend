package kr.flooding.backend.domain.user.dto.web.response

import kr.flooding.backend.domain.user.dto.common.response.SearchStudentResponse

data class SearchStudentListResponse(
    val students: List<SearchStudentResponse>,
)
