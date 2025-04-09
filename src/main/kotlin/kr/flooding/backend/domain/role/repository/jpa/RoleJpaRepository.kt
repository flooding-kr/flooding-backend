package kr.flooding.backend.domain.role.repository.jpa

import kr.flooding.backend.domain.role.entity.Role
import kr.flooding.backend.domain.user.entity.User
import kr.flooding.backend.domain.user.enums.RoleType
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface RoleJpaRepository : JpaRepository<Role, Long> {
	fun existsByUserAndType(
		user: User,
		roleType: RoleType,
	): Boolean

	fun deleteByUserAndType(
		user: User,
		roleType: RoleType,
	)

	fun findByUserId(userId: UUID): MutableList<Role>

	fun findByUser(user: User): MutableList<Role>

	fun user(user: User): MutableList<Role>
}
