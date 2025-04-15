package kr.flooding.backend.domain.clubMember.persistence.entity

import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import kr.flooding.backend.domain.club.persistence.entity.Club
import kr.flooding.backend.domain.user.persistence.entity.User
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import org.hibernate.annotations.UuidGenerator
import java.util.UUID

@Entity
class ClubMember(
	@Id
	@UuidGenerator
	val id: UUID? = null,

	@ManyToOne(fetch = FetchType.LAZY)
	@OnDelete(action = OnDeleteAction.CASCADE)
	val club: Club,

	@ManyToOne(fetch = FetchType.LAZY)
	@OnDelete(action = OnDeleteAction.CASCADE)
	val user: User,
)
