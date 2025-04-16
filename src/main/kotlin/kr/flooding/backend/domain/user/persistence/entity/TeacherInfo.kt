package kr.flooding.backend.domain.user.persistence.entity

import jakarta.persistence.Embeddable
import kr.flooding.backend.domain.user.enums.Department

@Embeddable
data class TeacherInfo(
	val department: Department,
)
