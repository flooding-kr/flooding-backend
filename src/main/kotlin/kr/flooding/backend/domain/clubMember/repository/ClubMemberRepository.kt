package kr.flooding.backend.domain.clubMember.repository

import kr.flooding.backend.domain.club.entity.ClubType
import kr.flooding.backend.domain.clubMember.entity.ClubMember
import kr.flooding.backend.domain.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional
import java.util.UUID

interface ClubMemberRepository : JpaRepository<ClubMember, UUID> {
	@Suppress("ktlint:standard:function-naming")
	fun existsByClub_TypeAndUser(
		type: ClubType,
		user: User,
	): Boolean

	fun findByClubIdAndUserId(
		clubId: UUID?,
		userId: UUID?,
	): Optional<ClubMember>
}
