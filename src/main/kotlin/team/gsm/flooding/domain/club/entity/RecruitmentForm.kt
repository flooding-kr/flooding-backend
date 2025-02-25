package team.gsm.flooding.domain.club.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import org.hibernate.annotations.UuidGenerator
import java.util.UUID

@Entity
class RecruitmentForm(
	@Id
	@UuidGenerator
	val id: UUID? = null,

	// id club_id,

	@OneToMany
	val recruitmentFormField: List<RecruitmentFormField>,
)
