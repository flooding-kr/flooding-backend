package kr.flooding.backend.domain.selfStudy.scheduler

import kr.flooding.backend.domain.selfStudy.persistence.repository.jpa.SelfStudyReservationJpaRepository
import kr.flooding.backend.domain.selfStudy.persistence.repository.jpa.SelfStudyRoomJpaRepository
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class SelfStudyScheduler(
    private val selfStudyReservationjpaRepository: SelfStudyReservationJpaRepository,
    private val selfStudyRoomRepository: SelfStudyRoomJpaRepository,
) {
    @Scheduled(cron = "0 0 0 * * *")
    fun clearReservation() {
        val selfStudyRoom = selfStudyRoomRepository.findAll().first()

        selfStudyReservationjpaRepository.deleteAll()
        selfStudyRoom.clearReservationCount()
    }
}
