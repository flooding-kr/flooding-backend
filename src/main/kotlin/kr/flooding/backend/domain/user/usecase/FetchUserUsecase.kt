package kr.flooding.backend.domain.user.usecase

import kr.flooding.backend.domain.user.dto.response.FetchUserInfoResponse
import kr.flooding.backend.domain.user.dto.response.StudentInfoResponse
import kr.flooding.backend.global.util.UserUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
@Transactional
class FetchUserUsecase(
	private val userUtil: UserUtil,
) {
	fun execute(): FetchUserInfoResponse {
		val user = userUtil.getUser()
		val studentInfo = user.studentInfo
		val nowDateYear = LocalDate.now().year

		val grade = nowDateYear - 2015 - user.studentInfo.year
		val isGraduate = grade > 3
		val studentInfoResponse =
			StudentInfoResponse(
				grade = if (isGraduate) 0 else grade,
				isGraduate = isGraduate,
				classroom = studentInfo.classroom,
				number = studentInfo.number,
				year = studentInfo.year,
			)

		return FetchUserInfoResponse(
			id = requireNotNull(user.id),
			name = user.name,
			gender = user.gender,
			studentInfo = studentInfoResponse,
			email = user.email,
		)
	}
}
