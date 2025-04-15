package kr.flooding.backend.domain.selfStudy.persistence.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint
import jakarta.persistence.Version
import kr.flooding.backend.domain.user.persistence.entity.User
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import java.time.LocalDate

@Entity
@Table(uniqueConstraints = [UniqueConstraint(columnNames = ["student_id"])])
class SelfStudyReservation(
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	val id: Long? = null,

	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	val student: User,

	@Version
	val version: Int = 0,

	@CreationTimestamp
	@Column(nullable = false)
	val createdAt: LocalDate = LocalDate.now(),
) {
	@Column(nullable = false)
	var isCancelled: Boolean = false
		protected set

	fun cancelReservation() {
		isCancelled = true
	}
}
