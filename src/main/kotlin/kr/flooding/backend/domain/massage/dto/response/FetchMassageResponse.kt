package kr.flooding.backend.domain.massage.dto.response

import kr.flooding.backend.domain.massage.enums.MassageStatus

class FetchMassageResponse (
    val currentCount: Int,
    val limit: Int,
    val status: MassageStatus,
)
