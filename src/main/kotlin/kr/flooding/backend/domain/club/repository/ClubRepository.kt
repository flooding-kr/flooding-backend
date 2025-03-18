package kr.flooding.backend.domain.club.repository

import kr.flooding.backend.domain.club.entity.Club
import kr.flooding.backend.domain.club.entity.ClubType
import kr.flooding.backend.domain.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.Optional
import java.util.UUID

interface ClubRepository : JpaRepository<Club, UUID> {
	fun existsByName(name: String): Boolean

	fun existsByTypeAndLeader(
		type: ClubType,
		leader: User,
	): Boolean

	@Query(
		"""
			SELECT c
			FROM Club c
			LEFT JOIN FETCH c.leader
			WHERE c.type = :type
	""",
	)
	fun findWithLeaderByType(type: ClubType): List<Club>

	@Query(
		"""
			SELECT c
			FROM Club c
			LEFT JOIN FETCH c.classroom
			LEFT JOIN FETCH c.classroom.teacher
			WHERE c.id = :id
	""",
	)
	fun findWithClassroomWithTeacherById(id: UUID): Optional<Club>
}
