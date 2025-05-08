package kr.flooding.backend.domain.notice.persistence.repository.jdsl

import kr.flooding.backend.domain.notice.persistence.entity.Notice
import kr.flooding.backend.domain.notice.persistence.entity.NoticeType

interface NoticeJdslRepository {
    fun findAllByNoticeTypesOrderByCreatedAt(noticeTypes: List<NoticeType>): List<Notice>
}