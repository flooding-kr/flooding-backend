package kr.flooding.backend.domain.selfStudy.scheduler

import kr.flooding.backend.domain.selfStudy.persistence.repository.SelfStudyReservationRepository
import kr.flooding.backend.domain.selfStudy.persistence.repository.SelfStudyRoomRepository
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class SelfStudyScheduler(
	private val selfStudyReservationRepository: SelfStudyReservationRepository,
	private val selfStudyRoomRepository: SelfStudyRoomRepository,
) {
	@Scheduled(cron = "0 0 0 * * *")
	fun clearReservation() {
		val selfStudyRoom = selfStudyRoomRepository.findAll().first()

		selfStudyReservationRepository.deleteAll()
		selfStudyRoom.clearReservationCount()
	}
}
