package kr.flooding.backend.domain.club.usecase.student

import kr.flooding.backend.domain.club.dto.web.request.CreateClubRequest
import kr.flooding.backend.domain.club.persistence.entity.Club
import kr.flooding.backend.domain.club.enums.ClubStatus
import kr.flooding.backend.domain.club.persistence.repository.ClubRepository
import kr.flooding.backend.domain.clubApplicant.persistence.repository.jpa.ClubApplicantJpaRepository
import kr.flooding.backend.domain.clubMember.persistence.entity.ClubMember
import kr.flooding.backend.domain.clubMember.persistence.repository.jpa.ClubMemberJpaRepository
import kr.flooding.backend.domain.homebaseTable.persistence.repository.jpa.ClassroomRepository
import kr.flooding.backend.global.exception.ExceptionEnum
import kr.flooding.backend.global.exception.HttpException
import kr.flooding.backend.global.exception.toPair
import kr.flooding.backend.global.util.UserUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import software.amazon.awssdk.services.s3.S3Client

@Service
@Transactional
class CreateClubUsecase(
	private val clubRepository: ClubRepository,
	private val classroomRepository: ClassroomRepository,
	private val clubApplicantJpaRepository: ClubApplicantJpaRepository,
	private val clubMemberJpaRepository: ClubMemberJpaRepository,
	private val userUtil: UserUtil,
	private val s3Client: S3Client,
) {
	fun execute(createClubRequest: CreateClubRequest) {
		// 이미 사용하고 있는 동아리명인지
		clubRepository
			.existsByName(createClubRequest.name)
			.takeIf { it }
			?.let {
				throw HttpException(ExceptionEnum.CLUB.ALREADY_USED_CLUB_NAME.toPair())
			}

		val currentUser = userUtil.getUser()

		// 이미 동일 유형의 동아리의 부장인지
		clubRepository
			.existsByTypeAndLeader(
				type = createClubRequest.type,
				leader = currentUser,
			).takeIf { it }
			?.let {
				throw HttpException(ExceptionEnum.CLUB.EXISTS_PENDING_CLUB.toPair())
			}

		// 동일 유형의 동아리 중 이미 참가한 동아리가 있는지
		clubMemberJpaRepository
			.existsByClubTypeAndUser(createClubRequest.type, currentUser)
			.takeIf { it }
			?.let {
				throw HttpException(ExceptionEnum.CLUB.ALREADY_JOINED_CLUB.toPair())
			}

		// 동일 유형의 동아리 중 지원한 동아리가 있는지
		clubApplicantJpaRepository
			.existsByClub_TypeAndUser(createClubRequest.type, currentUser)
			.takeIf { it }
			?.let {
				throw HttpException(ExceptionEnum.CLUB.ALREADY_APPLY_CLUB.toPair())
			}

		val classroom =
			classroomRepository.findById(createClubRequest.classroomId).orElseThrow {
				HttpException(ExceptionEnum.CLASSROOM.NOT_FOUND_CLASSROOM.toPair())
			}

		if (classroom.isHomebase) {
			throw HttpException(ExceptionEnum.CLASSROOM.IS_HOMEBASE_CLASSROOM.toPair())
		}

		val club =
			clubRepository.save(
				Club(
					name = createClubRequest.name,
					description = createClubRequest.description,
					classroom = classroom,
					activityImageKeys = createClubRequest.activityImageKeys,
					type = createClubRequest.type,
					thumbnailImageKey = createClubRequest.thumbnailImageKey,
					leader = currentUser,
					teacher = null,
				),
			)

		clubMemberJpaRepository.save(
			ClubMember(
				club = club,
				user = currentUser,
				clubType = club.type,
			),
		)
	}
}
