package team.gsm.flooding.domain.classroom.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
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

	@Column(nullable = false)
	val name: String,

	@Column(nullable = false)
	val description: String,

	val isHomebase: Boolean,

	@Enumerated(EnumType.STRING)
	val buildingType: BuildingType,

	@ManyToOne
	val teacher: User,
)
