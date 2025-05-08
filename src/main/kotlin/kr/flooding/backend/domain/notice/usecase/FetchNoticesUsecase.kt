package kr.flooding.backend.domain.notice.usecase

import kr.flooding.backend.domain.notice.controller.dto.common.response.NoticeResponse
import kr.flooding.backend.domain.notice.controller.dto.web.response.FetchNoticesResponse
import kr.flooding.backend.domain.notice.persistence.entity.NoticeType
import kr.flooding.backend.domain.notice.persistence.repository.jdsl.NoticeJdslRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class FetchNoticesUsecase(
    private val noticeJdslRepository: NoticeJdslRepository,
) {
    fun execute(noticeFilterType: List<NoticeType>): FetchNoticesResponse {
        val notices = noticeJdslRepository.findAllByNoticeTypesOrderByCreatedAt(noticeFilterType)

        return FetchNoticesResponse(
            notices.map { NoticeResponse.toDto(it) }
        )
    }
}