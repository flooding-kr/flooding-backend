package kr.flooding.backend.domain.user.usecase

import kr.flooding.backend.domain.user.dto.response.SearchTeacherListResponse
import kr.flooding.backend.domain.user.dto.response.SearchTeacherResponse
import kr.flooding.backend.domain.user.enums.RoleType
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
			userJdslRepository.findByNameContainsAndRolesContains(
				name,
				RoleType.ROLE_TEACHER,
			)

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
