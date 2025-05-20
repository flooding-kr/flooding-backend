package kr.flooding.backend.domain.selfStudy.persistence.entity

import jakarta.persistence.Column
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
) {
	@Column(nullable = false)
	var reservationLimit: Int = 50
		protected set

	fun updateLimit(limit: Int) {
		this.reservationLimit = limit
	}
}
