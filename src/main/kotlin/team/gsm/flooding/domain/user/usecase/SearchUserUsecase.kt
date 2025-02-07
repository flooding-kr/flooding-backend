package team.gsm.flooding.domain.user.usecase

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import team.gsm.flooding.domain.user.dto.response.FetchUserInfoResponse
import team.gsm.flooding.domain.user.dto.response.StudentInfoResponse
import team.gsm.flooding.domain.user.repository.UserRepository
import team.gsm.flooding.global.util.UserUtil
import java.time.LocalDate
import java.time.LocalDateTime

@Service
@Transactional
class SearchUserUsecase (
	private val userRepository: UserRepository,
) {
	fun execute(name: String): List<FetchUserInfoResponse> {
		val users = userRepository.findByNameContains(name)
		val nowDateYear = LocalDate.now().year

		return users.map { user ->
			val studentInfo = user.studentInfo
			val grade = nowDateYear - 2015 - user.studentInfo.year
			val isGraduate = grade > 3
			val studentInfoResponse = StudentInfoResponse(
				grade = if(isGraduate) 0 else grade,
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