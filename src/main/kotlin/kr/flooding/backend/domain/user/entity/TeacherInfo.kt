package kr.flooding.backend.domain.user.entity

import jakarta.persistence.Embeddable
import kr.flooding.backend.domain.user.enums.Department

@Embeddable
data class TeacherInfo(
	val department: Department,
)
