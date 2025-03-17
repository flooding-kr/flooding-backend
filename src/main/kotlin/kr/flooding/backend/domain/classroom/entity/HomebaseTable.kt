package kr.flooding.backend.domain.classroom.entity

import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction

@Entity
data class HomebaseTable(
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	val id: Long,

	@ManyToOne(fetch = FetchType.LAZY)
	@OnDelete(action = OnDeleteAction.CASCADE)
	val homebase: Classroom,
	val tableNumber: Int,
)
