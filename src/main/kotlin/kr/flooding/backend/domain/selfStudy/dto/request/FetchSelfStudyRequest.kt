package kr.flooding.backend.domain.selfStudy.dto.request

import kr.flooding.backend.domain.user.enums.Gender

class FetchSelfStudyRequest(
    val name: String? = null,
    val grade: Int? = null,
    val classroom: Int? = null,
    val gender: Gender? = null,
)
