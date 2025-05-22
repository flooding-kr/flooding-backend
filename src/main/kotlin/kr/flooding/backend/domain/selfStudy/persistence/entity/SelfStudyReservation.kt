package kr.flooding.backend.domain.selfStudy.persistence.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import kr.flooding.backend.domain.user.persistence.entity.User
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import org.hibernate.annotations.UuidGenerator
import java.time.LocalDateTime
import java.util.UUID

@Entity
class SelfStudyReservation(
	@Id
	@UuidGenerator
	val id: UUID? = null,

	@ManyToOne
	@JoinColumn(unique = true)
	@OnDelete(action = OnDeleteAction.CASCADE)
	val student: User,

	@CreationTimestamp
	@Column(nullable = false)
	val createdAt: LocalDateTime = LocalDateTime.now(),
) {
	@Column(nullable = false)
	var isCancelled: Boolean = false
		protected set

	@Column(nullable = false)
	var isPresent: Boolean = false
		protected set

	fun cancelReservation() {
		isCancelled = true
	}

	fun attendSelfStudy() {
		isPresent = true
	}

	fun absenceSelfStudy() {
		isPresent = false
	}
}
