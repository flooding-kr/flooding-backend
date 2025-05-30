package kr.flooding.backend.domain.clubApplicant.usecase

import kr.flooding.backend.domain.clubApplicant.dto.request.ApproveClubApplicantRequest
import kr.flooding.backend.domain.clubApplicant.dto.request.RejectClubApplicantRequest
import kr.flooding.backend.domain.clubApplicant.persistence.repository.jdsl.ClubApplicantJdslRepository
import kr.flooding.backend.domain.clubApplicant.persistence.repository.jpa.ClubApplicantJpaRepository
import kr.flooding.backend.domain.clubMember.persistence.entity.ClubMember
import kr.flooding.backend.domain.clubMember.persistence.repository.jpa.ClubMemberJpaRepository
import kr.flooding.backend.global.exception.ExceptionEnum
import kr.flooding.backend.global.exception.HttpException
import kr.flooding.backend.global.exception.toPair
import kr.flooding.backend.global.util.UserUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class RejectClubApplicantUsecase(
	private val userUtil: UserUtil,
	private val clubApplicantJdslRepository: ClubApplicantJdslRepository,
	private val clubApplicantJpaRepository: ClubApplicantJpaRepository,
) {
	fun execute(request: RejectClubApplicantRequest) {
		val applicant =
			clubApplicantJdslRepository.findById(request.applicantId).orElseThrow {
				HttpException(ExceptionEnum.CLUB.NOT_FOUND_CLUB_APPLICANT.toPair())
			}

		val currentUser = userUtil.getUser()
		if (currentUser != applicant.club.leader) {
			throw HttpException(ExceptionEnum.CLUB.NOT_CLUB_LEADER.toPair())
		}

		clubApplicantJpaRepository.delete(applicant)
	}
}
