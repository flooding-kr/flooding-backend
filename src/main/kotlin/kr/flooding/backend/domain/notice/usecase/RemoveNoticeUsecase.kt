package kr.flooding.backend.domain.notice.usecase

import kr.flooding.backend.domain.notice.persistence.repository.jpa.NoticeJpaRepository
import kr.flooding.backend.global.exception.ExceptionEnum
import kr.flooding.backend.global.exception.HttpException
import kr.flooding.backend.global.exception.toPair
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional
class RemoveNoticeUsecase(
    private val noticeJpaRepository: NoticeJpaRepository,
) {
    fun execute(noticeId: UUID) {
        val notice = noticeJpaRepository.findById(noticeId).orElseThrow {
            HttpException(ExceptionEnum.NOTICE.NOT_FOUND_NOTICE.toPair())
        }

        noticeJpaRepository.delete(notice)
    }
}