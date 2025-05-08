package kr.flooding.backend.domain.notice.controller.dto.web.request

data class CreateNoticeRequest(
    val title: String,
    val description: String,
)