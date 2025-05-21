package kr.flooding.backend.domain.music.persistence.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint
import jakarta.persistence.Version
import kr.flooding.backend.domain.user.persistence.entity.User
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import org.hibernate.annotations.UuidGenerator
import java.time.LocalDate
import java.time.LocalTime
import java.util.UUID

@Entity
@Table(uniqueConstraints = [UniqueConstraint(columnNames = ["user_id", "created_date"])])
class Music(
	@Id
	@UuidGenerator
	val id: UUID? = null,

	@Column(nullable = false)
	val musicUrl: String,

	@Column(nullable = false)
	val title: String,

	@Column(nullable = false)
	val thumbImageUrl: String,

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	val proposer: User,

	@CreationTimestamp
	@Column(nullable = false)
	val createdDate: LocalDate? = null,

	@CreationTimestamp
	@Column(nullable = false)
	val createdTime: LocalTime? = null,

	@Version
	val version: Long? = null,
) {
	@Column(nullable = false)
	var likeCount: Int = 0
		protected set

	fun incrementLikeCount() {
		likeCount++
	}

	fun decreaseLikeCount() {
		likeCount--
	}
}
