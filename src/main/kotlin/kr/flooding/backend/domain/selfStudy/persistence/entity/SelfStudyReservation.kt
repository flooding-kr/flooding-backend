package kr.flooding.backend.domain.selfStudy.persistence.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint
import kr.flooding.backend.domain.user.persistence.entity.User
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import org.hibernate.annotations.UuidGenerator
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(uniqueConstraints = [UniqueConstraint(columnNames = ["student_id", "created_at"])])
class SelfStudyReservation(
	@Id
	@UuidGenerator
	val id: UUID? = null,

	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	val student: User,

	@CreationTimestamp
	@Column(nullable = false)
	val createdAt: LocalDateTime = LocalDateTime.now(),
) {
	@Column(nullable = false)
	var isCancelled: Boolean = false
		protected set

	fun cancelReservation() {
		isCancelled = true
	}
}
