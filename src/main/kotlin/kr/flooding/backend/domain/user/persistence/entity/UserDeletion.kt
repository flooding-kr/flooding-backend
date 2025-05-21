package kr.flooding.backend.domain.user.persistence.entity

import jakarta.persistence.*
import kr.flooding.backend.domain.user.enums.Gender
import kr.flooding.backend.domain.user.enums.UserState
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import org.hibernate.annotations.UuidGenerator
import java.time.LocalDate
import java.util.UUID

@Entity
data class UserDeletion(
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	val id: Long? = null,

	@OneToOne(fetch = FetchType.LAZY)
	@OnDelete(action = OnDeleteAction.CASCADE)
	val user: User,

	@Column(nullable = false)
	val deletedDate: LocalDate
)