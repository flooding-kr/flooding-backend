package kr.flooding.backend.domain.homebaseTable.persistence.entity

import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint
import kr.flooding.backend.domain.classroom.persistence.entity.Classroom
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction

@Entity
@Table(
	name = "homebase_table",
	uniqueConstraints = [UniqueConstraint(columnNames = ["homebase_id", "table_number"])],
)
data class HomebaseTable(
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	val id: Long,

	@ManyToOne(fetch = FetchType.LAZY)
	@OnDelete(action = OnDeleteAction.CASCADE)
	val homebase: Classroom,

	val tableNumber: Int,

	val maxSeats: Int,
)
