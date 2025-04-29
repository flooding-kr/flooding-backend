package kr.flooding.backend.domain.massage.persistence.repository.jdsl

import kr.flooding.backend.domain.massage.persistence.entity.MassageReservation
import java.time.LocalDateTime

interface MassageReservationJdslRepository {
	fun findByCreatedAtBetweenAndIsCancelledAndOrderByCreatedAtAsc(
		startDateTime: LocalDateTime,
		endDateTime: LocalDateTime,
		isCancelled: Boolean
	): List<MassageReservation>
}