package kr.flooding.backend.domain.massage.dto.response

import kr.flooding.backend.domain.massage.enums.MassageStatus

class FetchMassageRankListResponse (
    val reservations: List<FetchMassageRankResponse>
)
