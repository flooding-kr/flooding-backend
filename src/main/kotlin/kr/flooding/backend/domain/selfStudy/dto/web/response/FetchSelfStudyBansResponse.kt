package kr.flooding.backend.domain.selfStudy.dto.web.response

import kr.flooding.backend.domain.selfStudy.dto.common.response.FetchSelfStudyBanResponse

data class FetchSelfStudyBansResponse(
    val selfStudyBans: List<FetchSelfStudyBanResponse>
)