package kr.flooding.backend.domain.music.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import kr.flooding.backend.domain.user.entity.User
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import org.hibernate.annotations.UuidGenerator
import java.time.LocalDate
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

	@Column(nullable = false)
	val likeCount: Int = 0,

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	val proposer: User,

	@CreationTimestamp
	@Column(nullable = false)
	val createdAt: LocalDate? = null
)
