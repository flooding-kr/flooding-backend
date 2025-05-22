package kr.flooding.backend.domain.homebaseParticipants.persistence.entity

import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint
import kr.flooding.backend.domain.homebase.persistence.entity.HomebaseGroup
import kr.flooding.backend.domain.user.persistence.entity.User
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import org.hibernate.annotations.UuidGenerator
import java.util.UUID

@Entity
@Table(
	name = "homebase_participant",
	uniqueConstraints = [UniqueConstraint(columnNames = ["homebase_group_id", "user_id"])]
)
class HomebaseParticipant(
	@Id
	@UuidGenerator
	val id: UUID? = null,

	@ManyToOne(fetch = FetchType.LAZY)
	@OnDelete(action = OnDeleteAction.CASCADE)
	val user: User,

	@ManyToOne(fetch = FetchType.LAZY)
	@OnDelete(action = OnDeleteAction.CASCADE)
	var homebaseGroup: HomebaseGroup,
)
