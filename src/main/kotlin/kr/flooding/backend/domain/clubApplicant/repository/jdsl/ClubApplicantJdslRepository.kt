package kr.flooding.backend.domain.clubApplicant.repository.jdsl

import kr.flooding.backend.domain.club.entity.Club
import kr.flooding.backend.domain.clubApplicant.domain.ClubApplicant
import kr.flooding.backend.domain.user.entity.User
import java.util.Optional
import java.util.UUID

interface ClubApplicantJdslRepository {
	fun findWithClubAndUserByClub(club: Club): List<ClubApplicant>

	fun findWithClubAndUserByUserAndClub(
		user: User,
		club: Club,
	): Optional<ClubApplicant>

	fun findById(id: UUID): Optional<ClubApplicant>
}
