package kr.flooding.backend.domain.period.persistence.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint
import java.time.LocalTime

@Entity
@Table(
	name = "period",
	uniqueConstraints = [UniqueConstraint(columnNames = ["start_time", "end_time"])]
)
data class Period(
	@Id
	val id: Int = 0,

	val startTime: LocalTime,

	val endTime: LocalTime,
)
