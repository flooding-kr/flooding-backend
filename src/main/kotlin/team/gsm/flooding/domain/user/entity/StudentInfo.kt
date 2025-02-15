package team.gsm.flooding.domain.user.entity

import jakarta.persistence.Embeddable

@Embeddable
data class StudentInfo(
	val year: Int,
	val classroom: Int,
	val number: Int,
)
