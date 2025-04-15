package kr.flooding.backend.domain.music.dto.response

import kr.flooding.backend.domain.user.entity.User
import kr.flooding.backend.global.util.StudentUtil

data class MusicProposerResponse(
	val name: String,
	val schoolNumber: String,
) {
	companion object {
		fun toDto(user: User): MusicProposerResponse {
			val studentInfo = requireNotNull(user.studentInfo) { "학생 정보가 없습니다." }

			val year = requireNotNull(studentInfo.year)
			val classroom = requireNotNull(studentInfo.classroom)
			val number = requireNotNull(studentInfo.number)

			return MusicProposerResponse(
				name = user.name,
				schoolNumber = StudentUtil.calcStudentNumber(year, classroom, number),
			)
		}
	}
}
