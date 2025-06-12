package kr.flooding.backend.domain.homebase.scheduler

import kr.flooding.backend.domain.homebase.persistence.repository.jpa.HomebaseGroupRepository
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class HomebaseScheduler(
    private val homebaseGroupRepository: HomebaseGroupRepository,
) {

    @Transactional
    @Scheduled(cron = "0 0 0 * * *")
    fun clearReservation() {
        homebaseGroupRepository.deleteAll()
    }
}