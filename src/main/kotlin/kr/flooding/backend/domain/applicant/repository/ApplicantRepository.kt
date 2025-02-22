package kr.flooding.backend.domain.applicant.repository

import kr.flooding.backend.domain.applicant.domain.Applicant
import kr.flooding.backend.domain.club.entity.ClubType
import kr.flooding.backend.domain.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface ApplicantRepository : JpaRepository<Applicant, UUID> {
	@Suppress("ktlint:standard:function-naming")
	fun existsByClub_TypeAndUser(
		type: ClubType,
		user: User,
	): Boolean
}
