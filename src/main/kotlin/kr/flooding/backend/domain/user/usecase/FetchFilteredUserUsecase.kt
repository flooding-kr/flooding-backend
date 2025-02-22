package kr.flooding.backend.domain.user.usecase

import kr.flooding.backend.domain.user.dto.response.FetchUserInfoResponse
import kr.flooding.backend.domain.user.dto.response.StudentInfoResponse
import kr.flooding.backend.domain.user.repository.UserRepository
import kr.flooding.backend.global.util.StudentUtil.Companion.calcGradeToYear
import kr.flooding.backend.global.util.StudentUtil.Companion.calcYearToGrade
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class FetchFilteredUserUsecase(
	private val userRepository: UserRepository,
) {
	fun execute(name: String): List<FetchUserInfoResponse> {
		val thirdGradeYear = calcGradeToYear(3)
		val users = userRepository.findByNameContainsAndStudentInfoYearGreaterThanEqual(name, thirdGradeYear)

		return users.map { user ->
			val studentInfo = user.studentInfo
			val grade = calcYearToGrade(user.studentInfo.year)
			val isGraduate = grade > 3
			val studentInfoResponse =
				StudentInfoResponse(
					grade = if (isGraduate) 0 else grade,
					isGraduate = isGraduate,
					classroom = studentInfo.classroom,
					number = studentInfo.number,
					year = studentInfo.year,
				)

			FetchUserInfoResponse(
				id = requireNotNull(user.id),
				name = user.name,
				gender = user.gender,
				studentInfo = studentInfoResponse,
				email = user.email,
			)
		}
	}
}
