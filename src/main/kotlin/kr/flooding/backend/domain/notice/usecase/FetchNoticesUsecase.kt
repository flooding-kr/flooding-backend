package kr.flooding.backend.domain.notice.usecase

import kr.flooding.backend.domain.notice.controller.dto.common.response.NoticeResponse
import kr.flooding.backend.domain.notice.controller.dto.web.response.FetchNoticesResponse
import kr.flooding.backend.domain.notice.persistence.repository.jpa.NoticeJpaRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class FetchNoticesUsecase(
    private val noticeJpaRepository: NoticeJpaRepository,
) {
    fun execute(): FetchNoticesResponse {
        val notices = noticeJpaRepository.findAllByOrderByCreatedAtDesc()

        return FetchNoticesResponse(
            notices.map { NoticeResponse.toDto(it) }
        )
    }
}