package kr.flooding.backend.domain.club.usecase

import jakarta.transaction.Transactional
import kr.flooding.backend.domain.club.dto.common.response.ClubStudentResponse
import kr.flooding.backend.domain.club.dto.common.response.ClubTeacherResponse
import kr.flooding.backend.domain.club.dto.web.response.FetchClubResponse
import kr.flooding.backend.domain.club.persistence.repository.ClubRepository
import kr.flooding.backend.domain.clubApplicant.persistence.repository.jpa.ClubApplicantJpaRepository
import kr.flooding.backend.domain.clubMember.persistence.repository.jdsl.ClubMemberJdslRepository
import kr.flooding.backend.global.exception.ExceptionEnum
import kr.flooding.backend.global.exception.HttpException
import kr.flooding.backend.global.exception.toPair
import kr.flooding.backend.global.thirdparty.s3.adapter.S3Adapter
import org.springframework.stereotype.Service
import java.util.UUID

@Service
@Transactional
class FetchClubUsecase(
	private val clubRepository: ClubRepository,
	private val clubMemberJdslRepository: ClubMemberJdslRepository,
	private val clubApplicantJpaRepository: ClubApplicantJpaRepository,
	private val s3Adapter: S3Adapter,
) {
	fun execute(clubId: UUID): FetchClubResponse {
		val club =
			clubRepository
				.findWithClassroomWithTeacherById(clubId)
				.orElseThrow { HttpException(ExceptionEnum.CLUB.NOT_FOUND_CLUB.toPair()) }

		val clubMembers = clubMemberJdslRepository.findWithUserAndClubByClubIdAndUserIsNot(clubId, club?.leader)

		val thumbnailImage = club.thumbnailImageKey?.let { s3Adapter.generatePresignedUrl(it) }
		val activityImages = club.activityImageKeys.map { s3Adapter.generatePresignedUrl(it) }

		val clubMemberResponses = clubMembers.map {
			val profileImage = it.user.profileImageKey?.let {
				s3Adapter.generatePresignedUrl(it)
			}
			ClubStudentResponse.toDto(it.user, profileImage)
		}

		val clubTeacherResponse = club.teacher?.let {
			val profileImageUrl = it.profileImageKey?.let { s3Adapter.generatePresignedUrl(it)}
			ClubTeacherResponse.toDto(it, profileImageUrl)
		}

		val leaderProfileImage = club.leader?.profileImageKey?.let {
			s3Adapter.generatePresignedUrl(it)
		}
		val clubLeaderResponse = club.leader?.let { ClubStudentResponse.toDto(it, leaderProfileImage) }

		val applicantCount = clubApplicantJpaRepository.countByClub(club)

		return FetchClubResponse.toDto(
			club = club,
			thumbnailImage = thumbnailImage,
			activityImages = activityImages,
			clubMembers = clubMemberResponses,
			teacher = clubTeacherResponse,
			leader = clubLeaderResponse,
			applicantCount = applicantCount,
		)
	}
}
