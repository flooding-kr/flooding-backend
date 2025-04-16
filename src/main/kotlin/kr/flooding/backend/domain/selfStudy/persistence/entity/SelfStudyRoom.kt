package kr.flooding.backend.domain.selfStudy.persistence.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Version

@Entity
class SelfStudyRoom(
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	val id: Long? = null,

	@Version
	val version: Int = 0,
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

	fun clearReservationCount() {
		reservationCount = 0
	}
}
