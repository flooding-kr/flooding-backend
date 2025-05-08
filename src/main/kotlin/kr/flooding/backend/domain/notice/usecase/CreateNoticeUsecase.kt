package kr.flooding.backend.domain.notice.usecase

import kr.flooding.backend.domain.notice.controller.dto.request.CreateNoticeRequest
import kr.flooding.backend.domain.notice.persistence.entity.Notice
import kr.flooding.backend.domain.notice.persistence.repository.jpa.NoticeJpaRepository
import kr.flooding.backend.global.util.UserUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class CreateNoticeUsecase(
    private val noticeJpaRepository: NoticeJpaRepository,
    private val userUtil: UserUtil,
) {
    fun execute(request: CreateNoticeRequest) {
        noticeJpaRepository.save(
            Notice(
                title = request.title,
                description = request.description,
                type = request.type,
                proposer = userUtil.getUser(),
            )
        )
    }
}