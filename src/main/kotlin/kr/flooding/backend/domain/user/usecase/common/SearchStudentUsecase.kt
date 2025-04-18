package kr.flooding.backend.domain.user.usecase.common

import kr.flooding.backend.domain.role.enums.RoleType
import kr.flooding.backend.domain.user.dto.web.response.SearchStudentListResponse
import kr.flooding.backend.domain.user.dto.common.response.SearchStudentResponse
import kr.flooding.backend.domain.user.enums.UserState
import kr.flooding.backend.domain.user.dto.common.response.StudentInfoResponse
import kr.flooding.backend.domain.user.persistence.repository.jdsl.UserJdslRepository
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
				val studentInfo = requireNotNull(user.studentInfo) { "학생 정보가 없습니다." }

				val year = requireNotNull(studentInfo.year)
				val classroom = requireNotNull(studentInfo.classroom)
				val number = requireNotNull(studentInfo.number)

				val grade = calcYearToGrade(year)
				val isGraduate = grade > 3
				val studentInfoResponse =
					StudentInfoResponse(
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
					studentInfo = studentInfoResponse,
					email = user.email,
				)
			},
		)
	}
}
