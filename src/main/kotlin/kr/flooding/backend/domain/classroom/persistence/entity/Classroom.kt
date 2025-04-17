package kr.flooding.backend.domain.classroom.persistence.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import kr.flooding.backend.domain.user.persistence.entity.User
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction

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

	@ManyToOne(fetch = FetchType.LAZY)
	@OnDelete(action = OnDeleteAction.SET_NULL)
	val teacher: User? = null,
)
