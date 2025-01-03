package team.gsm.flooding.domain.user.entity

import jakarta.persistence.Embeddable

@Embeddable
data class StudentInfo (
	private val grade: Int,
	private val classroom: Int,
	private val number: Int
)