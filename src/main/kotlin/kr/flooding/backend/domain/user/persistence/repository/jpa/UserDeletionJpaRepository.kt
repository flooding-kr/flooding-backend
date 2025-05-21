package kr.flooding.backend.domain.user.persistence.repository.jpa

import kr.flooding.backend.domain.user.persistence.entity.User
import kr.flooding.backend.domain.user.persistence.entity.UserDeletion
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDate
import java.util.UUID

interface UserDeletionJpaRepository : JpaRepository<UserDeletion, UUID> {
	fun existsByUser(user: User): Boolean

	fun findByDeletedDateBefore(date: LocalDate): List<UserDeletion>
}
