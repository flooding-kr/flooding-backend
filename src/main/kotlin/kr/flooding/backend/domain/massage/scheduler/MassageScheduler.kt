package kr.flooding.backend.domain.massage.scheduler

import kr.flooding.backend.domain.massage.persistence.repository.jpa.MassageJpaRepository
import kr.flooding.backend.domain.massage.persistence.repository.jpa.MassageReservationJpaRepository
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class MassageScheduler(
	private val massageReservationJpaRepository: MassageReservationJpaRepository,
	private val massageJpaRepository: MassageJpaRepository,
) {
	@Scheduled(cron = "0 0 0 * * *")
	fun clearReservation() {
		val selfStudyRoom = massageJpaRepository.findAll().first()

		massageReservationJpaRepository.deleteAll()
		selfStudyRoom.clearReservationCount()
	}
}
