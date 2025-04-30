package kr.flooding.backend.domain.user.usecase.common

import kr.flooding.backend.domain.role.enums.RoleType
import kr.flooding.backend.domain.user.dto.web.response.SearchStudentListResponse
import kr.flooding.backend.domain.user.dto.common.response.SearchStudentResponse
import kr.flooding.backend.domain.user.enums.UserState
import kr.flooding.backend.domain.user.persistence.repository.jdsl.UserJdslRepository
import kr.flooding.backend.domain.user.shared.StudentInfoModel
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

				SearchStudentResponse(
					id = requireNotNull(user.id),
					studentInfo = studentInfo.toModel(),
					name = user.name,
					gender = user.gender,
					email = user.email,
				)
			},
		)
	}
}
