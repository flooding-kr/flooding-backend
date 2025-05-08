package kr.flooding.backend.domain.notice.controller.dto.common.response

import kr.flooding.backend.domain.notice.persistence.entity.Notice
import java.time.LocalDate
import java.util.UUID

data class NoticeResponse(
    val noticeId: UUID?,
    val title: String,
    val description: String,
    val createdDate: LocalDate,
    val type: String,
) {
    companion object {
        fun toDto(notice: Notice): NoticeResponse =
            NoticeResponse(
                noticeId = notice.id,
                title = notice.title,
                description = notice.description,
                createdDate = notice.createdAt.toLocalDate(),
                type = notice.type.description,
            )
    }
}