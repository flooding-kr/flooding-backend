package kr.flooding.backend.domain.clubMember.repository

import kr.flooding.backend.domain.club.entity.ClubType
import kr.flooding.backend.domain.clubMember.entity.ClubMember
import kr.flooding.backend.domain.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.Optional
import java.util.UUID

interface ClubMemberRepository : JpaRepository<ClubMember, UUID> {
	@Suppress("ktlint:standard:function-naming")
	fun existsByClub_TypeAndUser(
		type: ClubType,
		user: User,
	): Boolean

	fun findByClubIdAndUserId(
		clubId: UUID,
		userId: UUID,
	): Optional<ClubMember>

	fun existsByClubIdAndUserId(
		clubId: UUID,
		userId: UUID,
	): Boolean

	@Query(
		"""
			SELECT cm
			FROM ClubMember cm
			JOIN FETCH cm.user
			WHERE cm.club.id = :clubId
	""",
	)
	fun findWithUserByClubId(clubId: UUID): List<ClubMember>
}
