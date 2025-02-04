package team.gsm.flooding.domain.classroom.entity

import jakarta.persistence.*
import team.gsm.flooding.domain.user.entity.User

@Entity
data class HomebaseTable (
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	val id: Long,

	@ManyToOne
	val homebase: Classroom,

	val tableNumber: Int
)