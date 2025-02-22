package team.gsm.flooding.domain.club.usecase

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import team.gsm.flooding.domain.applicant.repository.ApplicantRepository
import team.gsm.flooding.domain.club.dto.request.ApplyClubRequest
import team.gsm.flooding.domain.club.entity.ClubStatus
import team.gsm.flooding.domain.club.repository.ClubRepository
import team.gsm.flooding.domain.clubMember.entity.ClubMember
import team.gsm.flooding.domain.clubMember.repository.ClubMemberRepository
import team.gsm.flooding.global.exception.ExceptionEnum
import team.gsm.flooding.global.exception.HttpException
import team.gsm.flooding.global.util.UserUtil

@Service
@Transactional
class ApplyClubUsecase(
	private val clubRepository: ClubRepository,
	private val userUtil: UserUtil,
	private val clubMemberRepository: ClubMemberRepository,
	private val applicantRepository: ApplicantRepository,
) {
	fun execute(applyClubRequest: ApplyClubRequest) {
		val club =
			clubRepository.findById(applyClubRequest.clubId).orElseThrow {
				HttpException(ExceptionEnum.NOT_FOUND_CLUB)
			}

		val currentUser = userUtil.getUser()

		// 이미 동일 유형의 동아리의 부장인지
		clubRepository
			.existsByTypeAndLeader(
				type = club.type,
				leader = currentUser,
			).takeIf { it }
			?.let {
				throw HttpException(ExceptionEnum.EXISTS_PENDING_CLUB)
			}

		// 동일 유형의 동아리 중 이미 참가한 동아리가 있는지
		clubMemberRepository
			.existsByClub_TypeAndUser(club.type, currentUser)
			.takeIf { it }
			?.let {
				throw HttpException(ExceptionEnum.ALREADY_JOINED_CLUB)
			}

		// 동일 유형의 동아리 중 지원한 동아리가 있는지
		applicantRepository
			.existsByClub_TypeAndUser(club.type, currentUser)
			.takeIf { it }
			?.let {
				throw HttpException(ExceptionEnum.ALREADY_APPLY_CLUB)
			}

		if (club.status != ClubStatus.APPROVED) {
			throw HttpException(ExceptionEnum.NOT_APPROVED_CLUB)
		}

		if (!club.isRecruiting) {
			throw HttpException(ExceptionEnum.NOT_CLUB_RECRUITING)
		}

		clubMemberRepository.save(
			ClubMember(
				club = club,
				user = currentUser,
			),
		)
	}
}
