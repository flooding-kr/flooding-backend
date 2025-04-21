package kr.flooding.backend.domain.role.persistence.repository.jpa

import kr.flooding.backend.domain.role.enums.RoleType
import kr.flooding.backend.domain.role.persistence.entity.Role
import kr.flooding.backend.domain.user.persistence.entity.User
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
