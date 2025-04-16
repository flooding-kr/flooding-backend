package kr.flooding.backend.domain.clubApplicant.usecase

import kr.flooding.backend.domain.club.persistence.entity.ClubStatus
import kr.flooding.backend.domain.club.persistence.repository.ClubRepository
import kr.flooding.backend.domain.clubApplicant.dto.request.ApplyClubRequest
import kr.flooding.backend.domain.clubApplicant.persistence.entity.ClubApplicant
import kr.flooding.backend.domain.clubApplicant.persistence.repository.jpa.ClubApplicantJpaRepository
import kr.flooding.backend.domain.clubMember.persistence.repository.jpa.ClubMemberJpaRepository
import kr.flooding.backend.global.exception.ExceptionEnum
import kr.flooding.backend.global.exception.HttpException
import kr.flooding.backend.global.exception.toPair
import kr.flooding.backend.global.util.UserUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class ApplyClubUsecase(
	private val clubRepository: ClubRepository,
	private val userUtil: UserUtil,
	private val clubMemberJpaRepository: ClubMemberJpaRepository,
	private val clubApplicantJpaRepository: ClubApplicantJpaRepository,
) {
	fun execute(applyClubRequest: ApplyClubRequest) {
		val club =
			clubRepository.findById(applyClubRequest.clubId).orElseThrow {
				HttpException(ExceptionEnum.CLUB.NOT_FOUND_CLUB.toPair())
			}

		if (club.status != ClubStatus.APPROVED) {
			throw HttpException(ExceptionEnum.CLUB.NOT_APPROVED_CLUB.toPair())
		}

		if (!club.isRecruiting) {
			throw HttpException(ExceptionEnum.CLUB.NOT_CLUB_RECRUITING.toPair())
		}

		val currentUser = userUtil.getUser()

		// 동일 유형의 동아리 중 이미 참가한 동아리가 있는지
		clubMemberJpaRepository
			.existsByClub_TypeAndUser(club.type, currentUser)
			.takeIf { it }
			?.let {
				throw HttpException(ExceptionEnum.CLUB.ALREADY_JOINED_CLUB.toPair())
			}

		// 동일 유형의 동아리 중 지원한 동아리가 있는지
		clubApplicantJpaRepository
			.existsByClub_TypeAndUser(club.type, currentUser)
			.takeIf { it }
			?.let {
				throw HttpException(ExceptionEnum.CLUB.ALREADY_APPLY_CLUB.toPair())
			}

		clubApplicantJpaRepository.save(
			ClubApplicant(
				club = club,
				user = currentUser,
			),
		)
	}
}
