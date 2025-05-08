package kr.flooding.backend.domain.notice.persistence.repository.jpa

import kr.flooding.backend.domain.notice.persistence.entity.Notice
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface NoticeJpaRepository: JpaRepository<Notice, UUID> {
    fun findAllByOrderByCreatedAtDesc(): List<Notice>
}