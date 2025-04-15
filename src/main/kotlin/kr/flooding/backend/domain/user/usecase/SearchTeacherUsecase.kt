package kr.flooding.backend.domain.user.usecase

import kr.flooding.backend.domain.role.enums.RoleType
import kr.flooding.backend.domain.user.dto.response.SearchTeacherListResponse
import kr.flooding.backend.domain.user.dto.response.SearchTeacherResponse
import kr.flooding.backend.domain.user.enums.UserState
import kr.flooding.backend.domain.user.repository.jdsl.UserJdslRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class SearchTeacherUsecase(
	private val userJdslRepository: UserJdslRepository,

) {
	fun execute(name: String): SearchTeacherListResponse {
		val users =
			if (name.isEmpty()) {
				emptyList()
			} else {
				userJdslRepository.findByNameLikeAndRoleAndUserStateAndEmailVerifyStatus(
					name,
					RoleType.ROLE_TEACHER,
					UserState.APPROVED,
					true,
				)
			}

		return SearchTeacherListResponse(
			users.map { user ->
				SearchTeacherResponse(
					id = requireNotNull(user.id),
					name = user.name,
					gender = user.gender,
					email = user.email,
				)
			},
		)
	}
}
