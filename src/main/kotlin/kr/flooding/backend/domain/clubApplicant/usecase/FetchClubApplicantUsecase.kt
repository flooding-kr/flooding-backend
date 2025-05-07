package kr.flooding.backend.domain.clubApplicant.usecase

import kr.flooding.backend.domain.club.persistence.repository.ClubRepository
import kr.flooding.backend.domain.clubApplicant.dto.response.ClubApplicantResponse
import kr.flooding.backend.domain.clubApplicant.dto.response.FetchClubApplicantResponse
import kr.flooding.backend.domain.clubApplicant.persistence.repository.jdsl.ClubApplicantJdslRepository
import kr.flooding.backend.global.exception.ExceptionEnum
import kr.flooding.backend.global.exception.HttpException
import kr.flooding.backend.global.exception.toPair
import kr.flooding.backend.global.thirdparty.s3.adapter.S3Adapter
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
	private val s3Adapter: S3Adapter
) {
	fun execute(clubId: UUID): FetchClubApplicantResponse {
		val club = clubRepository.findById(clubId).orElseThrow {
			HttpException(ExceptionEnum.CLUB.NOT_FOUND_CLUB.toPair())
		}

		val currentUser = userUtil.getUser()
		val userId = requireNotNull(currentUser.id)

		if (currentUser != club.leader) {
			throw HttpException(ExceptionEnum.CLUB.NOT_CLUB_LEADER.toPair())
		}

		val clubApplicants = clubApplicantJdslRepository.findWithClubAndUserByClub(club)

		return FetchClubApplicantResponse(
			clubApplicants.map { applicant ->
				val user = applicant.user
				val applicantId = requireNotNull(applicant.id)
				val profileImage = user.profileImageKey?.let {
					s3Adapter.generatePresignedUrl(it)
				}

				ClubApplicantResponse(
					applicantId = applicantId,
					userId = userId,
					name = user.name,
					studentInfo = user.studentInfo?.toModel(),
					profileImage = profileImage,
				)
			},
		)
	}
}
