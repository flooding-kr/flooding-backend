package kr.flooding.backend.domain.massage.dto.response

import kr.flooding.backend.domain.massage.enums.MassageStatus

class FetchMassageRankResponse (
    val schoolNumber: String,
    val name: String,
    val profileImageUrl: String?
)
