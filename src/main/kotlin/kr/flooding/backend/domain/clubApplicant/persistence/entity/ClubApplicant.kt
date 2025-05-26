package kr.flooding.backend.domain.clubApplicant.persistence.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint
import kr.flooding.backend.domain.club.enums.ClubType
import kr.flooding.backend.domain.club.persistence.entity.Club
import kr.flooding.backend.domain.user.persistence.entity.User
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import org.hibernate.annotations.UuidGenerator
import java.util.UUID

@Entity
@Table(
	name = "club_applicant",
	uniqueConstraints = [UniqueConstraint(columnNames = ["user_id", "club_type"])]
)
class ClubApplicant(
	@Id
	@UuidGenerator
	val id: UUID? = null,

	@ManyToOne(fetch = FetchType.LAZY)
	@OnDelete(action = OnDeleteAction.CASCADE)
	val club: Club,

	@ManyToOne(fetch = FetchType.LAZY)
	@OnDelete(action = OnDeleteAction.CASCADE)
	val user: User,

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	val clubType: ClubType
)
