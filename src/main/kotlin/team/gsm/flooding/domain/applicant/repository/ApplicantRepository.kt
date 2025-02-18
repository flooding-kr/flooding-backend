package team.gsm.flooding.domain.applicant.repository

import org.springframework.data.jpa.repository.JpaRepository
import team.gsm.flooding.domain.applicant.domain.Applicant
import team.gsm.flooding.domain.club.entity.ClubType
import team.gsm.flooding.domain.user.entity.User
import java.util.UUID

interface ApplicantRepository : JpaRepository<Applicant, UUID> {
	@Suppress("ktlint:standard:function-naming")
	fun existsByClub_TypeAndUser(
		type: ClubType,
		user: User,
	): Boolean
}
