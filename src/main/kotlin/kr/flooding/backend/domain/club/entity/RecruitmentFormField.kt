package kr.flooding.backend.domain.club.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import org.hibernate.annotations.UuidGenerator
import java.util.UUID

@Entity
data class RecruitmentFormField(
	@Id
	@UuidGenerator
	val id: UUID? = null,

	val question: String,

	@ManyToOne
	val recruitmentForm: RecruitmentForm? = null,
)
