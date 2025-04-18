package kr.flooding.backend.domain.massage.persistence.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class Massage(
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	val id: Long? = null,
) {
	@Column(nullable = false)
	var reservationLimit: Int = 5
		protected set

	@Column(nullable = false)
	var reservationCount: Int = 0
		protected set

	fun updateLimit(limit: Int) {
		reservationLimit = limit
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
