package kr.flooding.backend.domain.user.repository.jdsl

import kr.flooding.backend.domain.user.entity.User
import kr.flooding.backend.domain.user.enums.RoleType
import kr.flooding.backend.global.security.model.UserCredential
import java.util.Optional
import java.util.UUID

interface UserJdslRepository {
	fun findByNameContainsAndYearGreaterThanEqualAndRolesContains(
		name: String,
		year: Int,
		role: RoleType,
	): List<User>

	fun findByNameContainsAndRolesContains(
		name: String,
		role: RoleType,
	): List<User>

	fun findCredentialById(userId: UUID): Optional<UserCredential>
}
