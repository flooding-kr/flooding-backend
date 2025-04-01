package kr.flooding.backend.domain.homebase.dto.response

import kr.flooding.backend.domain.homebaseParticipants.entity.HomebaseParticipant
import kr.flooding.backend.domain.user.entity.User
import kr.flooding.backend.global.util.StudentUtil

class HomebaseParticipantResponse(
	val name: String,
	val schoolNumber: String,
) {
	companion object {
		fun toDto(homebaseParticipant: HomebaseParticipant): HomebaseParticipantResponse {
			val studentInfo = homebaseParticipant.user.studentInfo

			val year = requireNotNull(studentInfo.year)
			val classroom = requireNotNull(studentInfo.classroom)
			val number = requireNotNull(studentInfo.number)

			return HomebaseParticipantResponse(
				name = homebaseParticipant.user.name,
				schoolNumber = StudentUtil.calcStudentNumber(year, classroom, number),
			)
		}

		fun fromUser(user: User): HomebaseParticipantResponse {
			val year = requireNotNull(user.studentInfo.year)
			val classroom = requireNotNull(user.studentInfo.classroom)
			val number = requireNotNull(user.studentInfo.number)

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
