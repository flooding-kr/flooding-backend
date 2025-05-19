package kr.flooding.backend.domain.clubMember.persistence.repository.jdsl

import kr.flooding.backend.domain.clubMember.persistence.entity.ClubMember
import kr.flooding.backend.domain.user.persistence.entity.User
import java.util.UUID

interface ClubMemberJdslRepository {
    fun findByUser(user: User): List<ClubMember>

    fun findWithUserAndClubByClubIdAndUserIsNot(
        clubId: UUID,
        user: User?,
    ): List<ClubMember>

    fun findWithUserAndClubByClubId(clubId: UUID): List<ClubMember>
}
