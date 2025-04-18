package kr.flooding.backend.domain.user.persistence.repository.jpa

import kr.flooding.backend.domain.user.enums.UserState
import kr.flooding.backend.domain.user.persistence.entity.StudentInfo
import kr.flooding.backend.domain.user.persistence.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional
import java.util.UUID

interface UserJpaRepository : JpaRepository<User, UUID> {
	fun findByEmail(email: String): Optional<User>

	fun existsByEmail(email: String): Boolean

	fun existsByStudentInfo(studentInfo: StudentInfo): Boolean

	fun findByIdIn(ids: List<UUID>): MutableList<User>

	fun findByState(userState: UserState): MutableList<User>
}
