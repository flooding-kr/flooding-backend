package kr.flooding.backend.domain.homebase.dto.response

import kr.flooding.backend.domain.homebaseParticipants.entity.HomebaseParticipant
import kr.flooding.backend.domain.user.entity.User
import kr.flooding.backend.global.util.StudentUtil

class HomebaseParticipantResponse(
	val name: String,
	val schoolNumber: String,
) {
	companion object {
		fun toDto(
			homebaseParticipant: HomebaseParticipant,
		): kr.flooding.backend.domain.homebase.dto.response.HomebaseParticipantResponse =
			kr.flooding.backend.domain.homebase.dto.response.HomebaseParticipantResponse(
				name = homebaseParticipant.user.name,
				schoolNumber =
					homebaseParticipant.user.studentInfo.let {
						StudentUtil.calcStudentNumber(it.year, it.classroom, it.number)
					},
			)

		fun fromUser(user: User): HomebaseParticipantResponse {
			return HomebaseParticipantResponse(
				name = user.name,
				schoolNumber =
					user.studentInfo.let {
						StudentUtil.calcStudentNumber(it.year, it.classroom, it.number)
					},
			)
		}
	}
}
