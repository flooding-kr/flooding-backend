package kr.flooding.backend.domain.selfStudy.scheduler

import kr.flooding.backend.domain.selfStudy.persistence.repository.jpa.SelfStudyBanJpaRepository
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Component
class SelfStudyBanScheduler(
    private val selfStudyBanJpaRepository: SelfStudyBanJpaRepository
) {
    @Scheduled(cron = "0 0 1 * * *")
    @Transactional
    fun removeExpiredSelfStudyBan() {
        selfStudyBanJpaRepository.deleteAllByResumeDateBefore(LocalDate.now())
    }
}
