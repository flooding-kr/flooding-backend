package team.gsm.flooding.domain.clubMember.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import org.hibernate.annotations.UuidGenerator
import team.gsm.flooding.domain.club.entity.Club
import team.gsm.flooding.domain.user.entity.User
import java.util.UUID

@Entity
class ClubMember(
	@Id
	@UuidGenerator
	val id: UUID? = null,

	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	val club: Club,

	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	val user: User,
)
