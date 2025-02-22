package kr.flooding.backend.domain.club.repository

import kr.flooding.backend.domain.club.entity.Club
import kr.flooding.backend.domain.club.entity.ClubType
import kr.flooding.backend.domain.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface ClubRepository : JpaRepository<Club, UUID> {
	fun existsByName(name: String): Boolean

	fun existsByTypeAndLeader(
		type: ClubType,
		leader: User,
	): Boolean

	fun findByType(type: ClubType): List<Club>
}
