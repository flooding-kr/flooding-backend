package kr.flooding.backend.domain.massage.scheduler

import kr.flooding.backend.domain.massage.persistence.repository.jpa.MassageRoomJpaRepository
import kr.flooding.backend.domain.massage.persistence.repository.jpa.MassageReservationJpaRepository
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class MassageScheduler(
	private val massageReservationJpaRepository: MassageReservationJpaRepository,
	private val massageRoomJpaRepository: MassageRoomJpaRepository,
) {
	@Scheduled(cron = "0 0 0 * * *")
	fun clearReservation() {
		val massageRoom = massageRoomJpaRepository.findAll().first()

		massageReservationJpaRepository.deleteAll()
		massageRoom.clearReservationCount()
	}
}
