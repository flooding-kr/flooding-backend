package team.gsm.flooding.domain.classroom.entity

import jakarta.persistence.*
import team.gsm.flooding.domain.user.entity.User

@Entity
data class Classroom (
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	val id: Long,

	val floor: Int,

	val name: String,

	val description: String,

	@ManyToOne
	val teacher: User
)