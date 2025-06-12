package kr.flooding.backend.domain.user.persistence.entity

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import kr.flooding.backend.domain.user.shared.StudentInfoModel
import kr.flooding.backend.global.util.StudentUtil
import java.time.LocalDate
import java.time.Month

@Embeddable
data class StudentInfo(
	@Column(nullable = true)
	val year: Int? = null,

	@Column(nullable = true)
	val classroom: Int? = null,

	@Column(nullable = true)
	val number: Int? = null,
) {
	fun isGraduated(): Boolean {
		val year = requireNotNull(this.year)
		val grade = StudentUtil.calcYearToGrade(year)

		val nowDate = LocalDate.now()
		val isRecentlyGraduated = grade == 4 && nowDate.month < Month.FEBRUARY
		val isGraduated = grade > 3

		return when {
			isRecentlyGraduated -> false
			isGraduated -> true
			else -> false
		}
	}

	fun toSchoolNumber(): String {
		return StudentUtil.calcStudentNumber(
			year = requireNotNull(this.year),
			classroom = requireNotNull(this.classroom),
			number = requireNotNull(this.number)
		)
	}

	fun toModel(): StudentInfoModel {
		val year = requireNotNull(this.year)
		val grade = StudentUtil.calcYearToGrade(year)
		val schoolNumber = StudentUtil.calcStudentNumber(
			year = requireNotNull(this.year),
			classroom = requireNotNull(this.classroom),
			number = requireNotNull(this.number)
		)

		return StudentInfoModel(
			schoolNumber = schoolNumber,
			grade = grade,
			classroom = requireNotNull(this.classroom),
			number = requireNotNull(this.number)
		)
	}
}
