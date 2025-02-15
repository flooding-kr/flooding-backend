package team.gsm.flooding.domain.user.repository

import org.springframework.data.jpa.repository.JpaRepository
import team.gsm.flooding.domain.user.entity.StudentInfo
import team.gsm.flooding.domain.user.entity.User
import java.util.Optional
import java.util.UUID

interface UserRepository : JpaRepository<User, UUID> {
	fun findByEmail(email: String): Optional<User>

	fun existsByEmail(email: String): Boolean

	fun existsByStudentInfo(studentInfo: StudentInfo): Boolean

	fun findByIdIn(ids: List<UUID>): MutableList<User>

	fun findByNameContainsAndStudentInfoYearGreaterThanEqual(
		name: String,
		year: Int,
	): List<User>
}
