package team.gsm.flooding.domain.classroom.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import team.gsm.flooding.domain.user.entity.User

@Entity
data class Classroom(
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	val id: Long,

	val floor: Int,

	val name: String,

	val description: String,

	val isHomebase: Boolean,

	@ManyToOne
	val teacher: User,
)
