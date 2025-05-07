package kr.flooding.backend.domain.user.persistence.entity

import jakarta.persistence.Embeddable
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import kr.flooding.backend.domain.user.enums.Department

@Embeddable
data class TeacherInfo(
	@Enumerated(EnumType.STRING)
	val department: Department,
)
