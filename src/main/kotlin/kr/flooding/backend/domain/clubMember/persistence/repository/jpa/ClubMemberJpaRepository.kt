package kr.flooding.backend.domain.clubMember.persistence.repository.jpa

import kr.flooding.backend.domain.club.enums.ClubType
import kr.flooding.backend.domain.clubMember.persistence.entity.ClubMember
import kr.flooding.backend.domain.user.persistence.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.Optional
import java.util.UUID

interface ClubMemberJpaRepository : JpaRepository<ClubMember, UUID> {
	fun existsByClubTypeAndUser(
		type: ClubType,
		user: User,
	): Boolean

	fun findByClubIdAndUserId(
		clubId: UUID,
		userId: UUID,
	): Optional<ClubMember>

	fun existsByClubIdAndUserId(
		clubId: UUID,
		userId: UUID?,
	): Boolean
}
