package kr.flooding.backend.domain.user.entity

import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
data class StudentInfo(
	@Column(nullable = true)
	val year: Int? = null,

	@Column(nullable = true)
	val classroom: Int? = null,

	@Column(nullable = true)
	val number: Int? = null,
)
