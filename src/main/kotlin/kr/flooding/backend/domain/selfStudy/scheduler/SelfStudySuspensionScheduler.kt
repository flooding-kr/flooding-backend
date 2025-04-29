package kr.flooding.backend.domain.selfStudy.scheduler

import kr.flooding.backend.domain.selfStudy.persistence.repository.jpa.SelfStudySuspensionJpaRepository
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Component
class SelfStudySuspensionScheduler(
    private val selfStudySuspensionJpaRepository: SelfStudySuspensionJpaRepository
) {
    @Scheduled(cron = "0 0 1 * * *")
    @Transactional
    fun clearReservation() {
        selfStudySuspensionJpaRepository.deleteAllByResumeDateBefore(LocalDate.now())
    }
}
