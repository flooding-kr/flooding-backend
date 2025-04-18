package kr.flooding.backend.domain.user.dto.web.response

import kr.flooding.backend.domain.user.dto.common.response.SearchTeacherResponse

data class SearchTeacherListResponse(
    val teachers: List<SearchTeacherResponse>,
)
