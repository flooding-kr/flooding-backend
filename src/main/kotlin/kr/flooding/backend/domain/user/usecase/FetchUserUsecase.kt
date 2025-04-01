package kr.flooding.backend.domain.user.usecase

import kr.flooding.backend.domain.user.dto.response.FetchUserInfoResponse
import kr.flooding.backend.domain.user.dto.response.StudentInfoResponse
import kr.flooding.backend.global.util.StudentUtil.Companion.calcYearToGrade
import kr.flooding.backend.global.util.UserUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.Optional

@Service
@Transactional
class FetchUserUsecase(
	private val userUtil: UserUtil,
) {
	fun execute(): FetchUserInfoResponse {
		val user = userUtil.getUser()
		val studentInfo = Optional.ofNullable(user.studentInfo)

		val studentInfoResponse =
			studentInfo.map {
				val year = requireNotNull(it.year)
				val classroom = requireNotNull(it.classroom)
				val number = requireNotNull(it.number)

				val grade = calcYearToGrade(year)
				val isGraduate = grade > 3
				StudentInfoResponse(
					grade = if (isGraduate) 0 else grade,
					isGraduate = isGraduate,
					classroom = classroom,
					number = number,
					year = year,
				)
			}

		return FetchUserInfoResponse(
			id = requireNotNull(user.id),
			name = user.name,
			gender = user.gender,
			studentInfo = if (studentInfoResponse.isPresent) studentInfoResponse.get() else null,
			email = user.email,
		)
	}
}
