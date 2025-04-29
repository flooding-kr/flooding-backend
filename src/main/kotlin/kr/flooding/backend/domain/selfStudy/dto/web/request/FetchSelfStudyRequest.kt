package kr.flooding.backend.domain.selfStudy.dto.web.request

import kr.flooding.backend.domain.user.enums.Gender

class FetchSelfStudyRequest(
    val name: String?,
    val grade: Int?,
    val classroom: Int?,
    val gender: Gender?,
)
