package team.gsm.flooding.domain.club.usecase

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import team.gsm.flooding.domain.applicant.repository.ApplicantRepository
import team.gsm.flooding.domain.classroom.repository.ClassroomRepository
import team.gsm.flooding.domain.club.dto.request.CreateClubRequest
import team.gsm.flooding.domain.club.entity.Club
import team.gsm.flooding.domain.club.entity.ClubStatus
import team.gsm.flooding.domain.club.repository.ClubRepository
import team.gsm.flooding.domain.clubMember.repository.ClubMemberRepository
import team.gsm.flooding.global.exception.ExceptionEnum
import team.gsm.flooding.global.exception.HttpException
import team.gsm.flooding.global.util.UserUtil

@Service
@Transactional
class CreateClubUsecase(
	private val clubRepository: ClubRepository,
	private val classroomRepository: ClassroomRepository,
	private val applicantRepository: ApplicantRepository,
	private val clubMemberRepository: ClubMemberRepository,
	private val userUtil: UserUtil,
) {
	fun execute(createClubRequest: CreateClubRequest) {
		// 이미 사용하고 있는 동아리명인지
		clubRepository
			.existsByName(createClubRequest.name)
			.takeIf { it }
			?.let {
				throw HttpException(ExceptionEnum.ALREADY_USED_CLUB_NAME)
			}

		val currentUser = userUtil.getUser()

		// 이미 동일 유형의 동아리의 부장인지
		clubRepository
			.existsByTypeAndLeader(
				type = createClubRequest.type,
				leader = currentUser,
			).takeIf { it }
			?.let {
				throw HttpException(ExceptionEnum.EXISTS_PENDING_CLUB)
			}

		// 동일 유형의 동아리 중 이미 참가한 동아리가 있는지
		clubMemberRepository
			.existsByClub_TypeAndUser(createClubRequest.type, currentUser)
			.takeIf { it }
			?.let {
				throw HttpException(ExceptionEnum.ALREADY_JOINED_CLUB)
			}

		// 동일 유형의 동아리 중 지원한 동아리가 있는지
		applicantRepository
			.existsByClub_TypeAndUser(createClubRequest.type, currentUser)
			.takeIf { it }
			?.let {
				throw HttpException(ExceptionEnum.ALREADY_APPLY_CLUB)
			}

		val classroom =
			classroomRepository.findById(createClubRequest.classroomId).orElseThrow {
				HttpException(ExceptionEnum.NOT_FOUND_CLASSROOM)
			}

		if (classroom.isHomebase) {
			throw HttpException(ExceptionEnum.IS_HOMEBASE_CLASSROOM)
		}

		clubRepository.save(
			Club(
				name = createClubRequest.name,
				description = createClubRequest.description,
				classroom = classroom,
				activityImageUrls = createClubRequest.activityImageUrls,
				status = ClubStatus.PENDING,
				type = createClubRequest.type,
				thumbnailImageUrl = createClubRequest.mainImageUrl,
				leader = currentUser,
			),
		)
	}
}
