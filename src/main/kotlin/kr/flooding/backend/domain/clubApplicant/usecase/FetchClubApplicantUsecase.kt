package kr.flooding.backend.domain.clubApplicant.usecase

import kr.flooding.backend.domain.club.repository.ClubRepository
import kr.flooding.backend.domain.clubApplicant.dto.response.ClubApplicantResponse
import kr.flooding.backend.domain.clubApplicant.dto.response.FetchClubApplicantResponse
import kr.flooding.backend.domain.clubApplicant.repository.jdsl.ClubApplicantJdslRepository
import kr.flooding.backend.global.exception.ExceptionEnum
import kr.flooding.backend.global.exception.HttpException
import kr.flooding.backend.global.exception.toPair
import kr.flooding.backend.global.util.UserUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
@Transactional
class FetchClubApplicantUsecase(
	private val clubRepository: ClubRepository,
	private val userUtil: UserUtil,
	private val clubApplicantJdslRepository: ClubApplicantJdslRepository,
) {
	fun execute(clubId: UUID): FetchClubApplicantResponse {
		val club =
			clubRepository.findById(clubId).orElseThrow {
				HttpException(ExceptionEnum.CLUB.NOT_FOUND_CLUB.toPair())
			}

		val currentUser = userUtil.getUser()
		val userId = requireNotNull(currentUser.id)

		if (currentUser != club.leader) {
			throw HttpException(ExceptionEnum.CLUB.NOT_CLUB_LEADER.toPair())
		}

		val clubApplicants = clubApplicantJdslRepository.findWithClubAndUserByClub(club)

		return FetchClubApplicantResponse(
			clubApplicants.map {
				val applicantId = requireNotNull(it.id)
				ClubApplicantResponse(
					applicantId = applicantId,
					userId = userId,
					name = it.user.name,
				)
			},
		)
	}
}
