package kr.flooding.backend.global.util

import kr.flooding.backend.domain.role.entity.Role
import kr.flooding.backend.domain.role.repository.jpa.RoleJpaRepository
import kr.flooding.backend.domain.user.entity.User
import kr.flooding.backend.domain.user.enums.RoleType
import org.springframework.stereotype.Component

@Component
class RoleUtil(
	private val roleJpaRepository: RoleJpaRepository,
) {
	fun saveRoles(
		user: User,
		types: List<RoleType>,
	) {
		val prevRoles = roleJpaRepository.findByUser(user).map { it.type }
		val roles =
			types
				.map {
					Role(
						user = user,
						type = it,
					)
				}.filter {
					!prevRoles.contains(it.type)
				}

		roleJpaRepository.saveAll(roles)
	}
}
