package kr.flooding.backend.domain.club.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.OneToOne
import org.hibernate.annotations.UuidGenerator
import java.util.UUID

@Entity
class RecruitmentForm(
	@Id
	@UuidGenerator
	val id: UUID? = null,

	@OneToOne
	val club: Club,

	@OneToMany
	val recruitmentFormField: List<RecruitmentFormField>,
)
