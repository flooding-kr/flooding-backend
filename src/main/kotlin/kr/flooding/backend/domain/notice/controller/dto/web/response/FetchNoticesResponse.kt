package kr.flooding.backend.domain.notice.controller.dto.web.response

import kr.flooding.backend.domain.notice.controller.dto.common.response.NoticeResponse

data class FetchNoticesResponse(
    val notices: List<NoticeResponse>,
)