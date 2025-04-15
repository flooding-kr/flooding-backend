package kr.flooding.backend.domain.music.persistence.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Version
import kr.flooding.backend.domain.user.entity.User
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import org.hibernate.annotations.UuidGenerator
import java.time.LocalDateTime
import java.util.UUID

@Entity
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
	val createdAt: LocalDateTime? = null,

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
