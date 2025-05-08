package kr.flooding.backend.domain.notice.controller.dto.web.request

import kr.flooding.backend.domain.notice.persistence.entity.NoticeType

data class CreateNoticeRequest(
    val title: String,
    val description: String,
    val type: NoticeType,
)