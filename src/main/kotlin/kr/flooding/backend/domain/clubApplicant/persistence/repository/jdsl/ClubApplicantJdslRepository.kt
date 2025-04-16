package kr.flooding.backend.domain.clubApplicant.persistence.repository.jdsl

import kr.flooding.backend.domain.club.persistence.entity.Club
import kr.flooding.backend.domain.clubApplicant.persistence.entity.ClubApplicant
import kr.flooding.backend.domain.user.persistence.entity.User
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
