package kr.flooding.backend.domain.selfStudy.dto.web.response

import kr.flooding.backend.domain.selfStudy.enums.SelfStudyStatus

class SelfStudyStatusResponse(
    val currentCount: Int,
    val limit: Int,
    val status: SelfStudyStatus,
)
