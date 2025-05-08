package kr.flooding.backend.domain.notice.controller.dto.request

import kr.flooding.backend.domain.notice.persistence.entity.NoticeType

data class CreateNoticeRequest(
    val title: String,
    val description: String,
    val type: NoticeType,
)