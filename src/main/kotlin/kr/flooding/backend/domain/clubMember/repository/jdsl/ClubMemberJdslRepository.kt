package kr.flooding.backend.domain.clubMember.repository.jdsl

import kr.flooding.backend.domain.clubMember.entity.ClubMember
import kr.flooding.backend.domain.user.entity.User

interface ClubMemberJdslRepository {
	fun findByUser(user: User): List<ClubMember>
}
