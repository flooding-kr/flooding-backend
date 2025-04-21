package kr.flooding.backend.domain.club.persistence.repository

import kr.flooding.backend.domain.club.persistence.entity.Club
import kr.flooding.backend.domain.club.enums.ClubStatus
import kr.flooding.backend.domain.club.enums.ClubType
import kr.flooding.backend.domain.user.persistence.entity.User
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
			WHERE
				c.type = :type AND
				c.status = :status
	""",
	)
	fun findWithLeaderByTypeAndStatus(
        type: ClubType,
        status: ClubStatus,
	): List<Club>

	@Query(
		"""
			SELECT c
			FROM Club c
			LEFT JOIN FETCH c.leader
			WHERE c.status = :status
	""",
	)
	fun findWithLeaderAndStatus(status: ClubStatus): List<Club>

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
