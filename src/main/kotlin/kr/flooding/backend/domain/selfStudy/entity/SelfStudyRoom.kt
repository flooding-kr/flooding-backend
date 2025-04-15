package kr.flooding.backend.domain.selfStudy.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class SelfStudyRoom(
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	val id: Long? = null,
) {
	var reservationLimit: Int = 50
		protected set

	var reservationCount: Int = 0
		protected set

	fun updateLimit(limit: Int) {
		this.reservationLimit = limit
	}

	fun incrementReservationCount() {
		reservationCount++
	}

	fun decrementReservationCount() {
		reservationCount--
	}
}
