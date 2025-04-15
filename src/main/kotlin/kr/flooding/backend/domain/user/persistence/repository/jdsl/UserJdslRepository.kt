package kr.flooding.backend.domain.user.persistence.repository.jdsl

import kr.flooding.backend.domain.role.enums.RoleType
import kr.flooding.backend.domain.user.enums.UserState
import kr.flooding.backend.domain.user.persistence.entity.User
import kr.flooding.backend.global.security.model.UserCredential
import java.util.Optional
import java.util.UUID

interface UserJdslRepository {
	fun findByNameLikeAndYearGreaterThanEqualAndRoleAndUserStateAndEmailVerifyStatus(
		name: String,
		year: Int,
		role: RoleType,
		userState: UserState,
		emailVerifyStatus: Boolean,
	): List<User>

	fun findByNameLikeAndRoleAndUserStateAndEmailVerifyStatus(
		name: String,
		role: RoleType,
		userState: UserState,
		emailVerifyStatus: Boolean,
	): List<User>

	fun findCredentialById(userId: UUID): Optional<UserCredential>
}
