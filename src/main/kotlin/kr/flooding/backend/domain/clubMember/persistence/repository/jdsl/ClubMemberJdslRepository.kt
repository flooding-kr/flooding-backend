package kr.flooding.backend.domain.clubMember.persistence.repository.jdsl

import kr.flooding.backend.domain.clubMember.persistence.entity.ClubMember
import kr.flooding.backend.domain.user.persistence.entity.User

interface ClubMemberJdslRepository {
	fun findByUser(user: User): List<ClubMember>
}
