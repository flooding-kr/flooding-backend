package team.gsm.flooding.domain.clubMember.repository

import org.springframework.data.jpa.repository.JpaRepository
import team.gsm.flooding.domain.club.entity.ClubType
import team.gsm.flooding.domain.clubMember.entity.ClubMember
import team.gsm.flooding.domain.user.entity.User
import java.util.UUID

interface ClubMemberRepository : JpaRepository<ClubMember, UUID> {
	@Suppress("ktlint:standard:function-naming")
	fun existsByClub_TypeAndUser(
		type: ClubType,
		user: User,
	): Boolean
}
