package team.gsm.flooding.domain.user.entity

import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
data class StudentInfo(
	@Column(nullable = false)
	val year: Int,

	@Column(nullable = false)
	val classroom: Int,

	@Column(nullable = false)
	val number: Int,
)
