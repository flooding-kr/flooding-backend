package kr.flooding.backend.domain.user.dto.common.response

import kr.flooding.backend.domain.user.persistence.entity.StudentInfo
import kr.flooding.backend.domain.user.persistence.entity.User
import kr.flooding.backend.global.util.StudentUtil.Companion.calcYearToGrade

data class StudentInfoResponse(
	val isGraduate: Boolean,
	val grade: Int,
	val classroom: Int,
	val number: Int,
	val year: Int,
) {
	companion object {
		fun toDto(studentInfo: StudentInfo): StudentInfoResponse {
			val year = requireNotNull(studentInfo.year)
			val grade = calcYearToGrade(year)
			val isGraduate = grade > 3
			return StudentInfoResponse(
				year = year,
				isGraduate =  isGraduate,
				grade = grade,
				classroom = requireNotNull(studentInfo.classroom),
				number = requireNotNull(studentInfo.number)
			)
		}
	}
}
