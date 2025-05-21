package kr.flooding.backend.domain.selfStudy.scheduler

import kr.flooding.backend.domain.selfStudy.persistence.repository.jpa.SelfStudyBanJpaRepository
import kr.flooding.backend.domain.selfStudy.usecase.BanForSelfStudyAbsenceUsecase
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Component
class SelfStudyBanScheduler(
    private val banForSelfStudyAbsenceUsecase: BanForSelfStudyAbsenceUsecase,
    private val selfStudyBanJpaRepository: SelfStudyBanJpaRepository,
) {
    @Scheduled(cron = "0 59 23 * * *")
    fun banForSelfStudyAbsence() {
        banForSelfStudyAbsenceUsecase.execute()
    }

    @Scheduled(cron = "0 0 1 * * *")
    @Transactional
    fun removeExpiredSelfStudyBan() {
        selfStudyBanJpaRepository.deleteAllByResumeDateBefore(LocalDate.now())
    }
}
