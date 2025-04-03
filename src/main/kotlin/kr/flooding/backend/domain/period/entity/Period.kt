package kr.flooding.backend.domain.period.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.time.LocalTime

@Entity
data class Period(
	@Id
	val id: Int = 0,

	val startTime: LocalTime,

	val endTime: LocalTime,
)
