package team.gsm.flooding.domain.classroom.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import team.gsm.flooding.domain.user.entity.User

@Entity
data class Classroom (
	@Id
	val slug: String,

	val floor: Int,

	val name: String,

	val description: String,

	@ManyToOne
	val teacher: User
)