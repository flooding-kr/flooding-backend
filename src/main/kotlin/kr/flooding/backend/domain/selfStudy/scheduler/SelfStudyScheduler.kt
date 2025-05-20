package kr.flooding.backend.domain.selfStudy.scheduler

import kr.flooding.backend.domain.selfStudy.persistence.repository.jpa.SelfStudyReservationJpaRepository
import kr.flooding.backend.domain.selfStudy.persistence.repository.jpa.SelfStudyRoomJpaRepository
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class SelfStudyScheduler(
    private val selfStudyReservationJpaRepository: SelfStudyReservationJpaRepository,
    private val selfStudyRoomRepository: SelfStudyRoomJpaRepository,
) {
    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    fun clearReservation() {
        selfStudyReservationJpaRepository.deleteAll()
    }
}
