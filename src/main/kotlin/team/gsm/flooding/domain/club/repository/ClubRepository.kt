package team.gsm.flooding.domain.club.repository

import org.springframework.data.jpa.repository.JpaRepository
import team.gsm.flooding.domain.club.entity.Club
import team.gsm.flooding.domain.club.entity.ClubType
import team.gsm.flooding.domain.user.entity.User
import java.util.UUID

interface ClubRepository : JpaRepository<Club, UUID> {
	fun existsByName(name: String): Boolean

	fun existsByTypeAndLeader(
		type: ClubType,
		leader: User,
	): Boolean
}
