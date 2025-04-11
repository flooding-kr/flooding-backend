package kr.flooding.backend.domain.user.usecase

import kr.flooding.backend.domain.role.enums.RoleType
import kr.flooding.backend.domain.user.dto.response.SearchStudentListResponse
import kr.flooding.backend.domain.user.dto.response.SearchStudentResponse
import kr.flooding.backend.domain.user.enums.UserState
import kr.flooding.backend.domain.user.model.StudentInfoModel
import kr.flooding.backend.domain.user.repository.jdsl.UserJdslRepository
import kr.flooding.backend.global.util.StudentUtil.Companion.calcGradeToYear
import kr.flooding.backend.global.util.StudentUtil.Companion.calcYearToGrade
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class SearchStudentUsecase(
	private val userJdslRepository: UserJdslRepository,
) {
	fun execute(name: String): SearchStudentListResponse {
		val thirdGradeYear = calcGradeToYear(3)
		val users =
			if (name.isEmpty()) {
				emptyList()
			} else {
				userJdslRepository.findByNameLikeAndYearGreaterThanEqualAndRoleAndUserStateAndEmailVerifyStatus(
					name,
					thirdGradeYear,
					RoleType.ROLE_STUDENT,
					UserState.APPROVED,
					true,
				)
			}

		return SearchStudentListResponse(
			users.map { user ->
				val year = requireNotNull(user.studentInfo.year)
				val classroom = requireNotNull(user.studentInfo.classroom)
				val number = requireNotNull(user.studentInfo.number)

				val grade = calcYearToGrade(year)
				val isGraduate = grade > 3
				val studentInfoModel =
					StudentInfoModel(
						grade = if (isGraduate) 0 else grade,
						isGraduate = isGraduate,
						classroom = classroom,
						number = number,
						year = year,
					)

				SearchStudentResponse(
					id = requireNotNull(user.id),
					name = user.name,
					gender = user.gender,
					studentInfo = studentInfoModel,
					email = user.email,
				)
			},
		)
	}
}
