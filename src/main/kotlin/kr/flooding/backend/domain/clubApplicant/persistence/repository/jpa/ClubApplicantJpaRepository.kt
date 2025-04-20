package kr.flooding.backend.domain.clubApplicant.persistence.repository.jpa

import kr.flooding.backend.domain.club.persistence.entity.Club
import kr.flooding.backend.domain.club.persistence.entity.ClubType
import kr.flooding.backend.domain.clubApplicant.persistence.entity.ClubApplicant
import kr.flooding.backend.domain.user.persistence.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface ClubApplicantJpaRepository : JpaRepository<ClubApplicant, UUID> {
	@Suppress("ktlint:standard:function-naming")
	fun existsByClub_TypeAndUser(
		type: ClubType,
		user: User,
	): Boolean

	fun countByClub(club: Club): Int
}
