package team.gsm.flooding.domain.club.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import org.hibernate.annotations.UuidGenerator
import java.util.UUID

@Entity
class RecruitmentFormField(
	@Id
	@UuidGenerator
	val id: UUID? = null,

	val question: String,
)
