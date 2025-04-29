package kr.flooding.backend.domain.user.persistence.entity

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import kr.flooding.backend.global.util.StudentUtil

@Embeddable
data class StudentInfo(
	@Column(nullable = true)
	val year: Int? = null,

	@Column(nullable = true)
	val classroom: Int? = null,

	@Column(nullable = true)
	val number: Int? = null,
) {
	fun toSchoolNumber(): String {
		return StudentUtil.calcStudentNumber(
			year = requireNotNull(this.year),
			classroom = requireNotNull(this.classroom),
			number = requireNotNull(this.number)
		)
	}
}
