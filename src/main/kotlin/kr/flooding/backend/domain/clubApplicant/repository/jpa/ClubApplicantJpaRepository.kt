package kr.flooding.backend.domain.clubApplicant.repository.jpa

import kr.flooding.backend.domain.club.entity.ClubType
import kr.flooding.backend.domain.clubApplicant.domain.ClubApplicant
import kr.flooding.backend.domain.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface ClubApplicantJpaRepository : JpaRepository<ClubApplicant, UUID> {
	@Suppress("ktlint:standard:function-naming")
	fun existsByClub_TypeAndUser(
		type: ClubType,
		user: User,
	): Boolean
}
