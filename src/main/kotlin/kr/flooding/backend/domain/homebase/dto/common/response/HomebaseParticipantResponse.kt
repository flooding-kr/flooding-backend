package kr.flooding.backend.domain.homebase.dto.common.response

import kr.flooding.backend.domain.homebaseParticipants.persistence.entity.HomebaseParticipant
import kr.flooding.backend.domain.user.persistence.entity.User
import kr.flooding.backend.global.util.StudentUtil

class HomebaseParticipantResponse(
	val name: String,
	val schoolNumber: String,
) {
	companion object {
		fun toDto(homebaseParticipant: HomebaseParticipant): HomebaseParticipantResponse {
			val studentInfo = requireNotNull(homebaseParticipant.user.studentInfo) { "학생 정보가 없습니다." }

			val year = requireNotNull(studentInfo.year)
			val classroom = requireNotNull(studentInfo.classroom)
			val number = requireNotNull(studentInfo.number)

			return HomebaseParticipantResponse(
				name = homebaseParticipant.user.name,
				schoolNumber = StudentUtil.calcStudentNumber(year, classroom, number),
			)
		}

		fun fromUser(user: User): HomebaseParticipantResponse {
			val studentInfo = requireNotNull(user.studentInfo) { "학생 정보가 없습니다." }

			val year = requireNotNull(studentInfo.year)
			val classroom = requireNotNull(studentInfo.classroom)
			val number = requireNotNull(studentInfo.number)

			return HomebaseParticipantResponse(
				name = user.name,
				schoolNumber =
					user.studentInfo.let {
						StudentUtil.calcStudentNumber(year, classroom, number)
					},
			)
		}
	}
}
