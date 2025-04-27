package kr.flooding.backend.domain.club.usecase

import jakarta.transaction.Transactional
import kr.flooding.backend.domain.club.dto.common.response.ClubStudentResponse
import kr.flooding.backend.domain.club.dto.common.response.ClubTeacherResponse
import kr.flooding.backend.domain.club.dto.web.response.FetchClubResponse
import kr.flooding.backend.domain.club.persistence.repository.ClubRepository
import kr.flooding.backend.domain.clubApplicant.persistence.repository.jpa.ClubApplicantJpaRepository
import kr.flooding.backend.domain.clubMember.persistence.repository.jdsl.ClubMemberJdslRepository
import kr.flooding.backend.domain.clubMember.persistence.repository.jpa.ClubMemberJpaRepository
import kr.flooding.backend.global.exception.ExceptionEnum
import kr.flooding.backend.global.exception.HttpException
import kr.flooding.backend.global.exception.toPair
import kr.flooding.backend.global.util.FileUtil
import org.springframework.stereotype.Service
import java.util.UUID

@Service
@Transactional
class FetchClubUsecase(
	private val clubRepository: ClubRepository,
	private val clubMemberJpaRepository: ClubMemberJpaRepository,
	private val clubMemberJdslRepository: ClubMemberJdslRepository,
	private val clubApplicantJpaRepository: ClubApplicantJpaRepository,
	private val fileUtil: FileUtil,
) {
	fun execute(clubId: UUID): FetchClubResponse {
		val club =
			clubRepository
				.findWithClassroomWithTeacherById(clubId)
				.orElseThrow { HttpException(ExceptionEnum.CLUB.NOT_FOUND_CLUB.toPair()) }

		val clubMembers = clubMemberJdslRepository.findWithUserAndClubByClubIdAndUserIsNot(clubId, club.leader)

		val thumbnailImageUrl = club.thumbnailImageKey?.let { fileUtil.generatePresignedUrl(it) }
		val activityImageUrls = club.activityImageKeys.map { fileUtil.generatePresignedUrl(it) }

		val clubMemberResponses = clubMembers.map {
			val profileImageUrl = it.user.profileImageKey?.let {fileUtil.generatePresignedUrl(it)}
			ClubStudentResponse.toDto(it.user, profileImageUrl)
		}

		val clubTeacherResponse = club.teacher?.let {
			val profileImageUrl = it.profileImageKey?.let {fileUtil.generatePresignedUrl(it)}
			ClubTeacherResponse.toDto(it, profileImageUrl)
		}

		val leaderProfileImageUrl = club.leader.profileImageKey?.let { fileUtil.generatePresignedUrl(it) }
		val clubLeaderResponse = ClubStudentResponse.toDto(club.leader, leaderProfileImageUrl)

		val applicantCount = clubApplicantJpaRepository.countByClub(club)

		return FetchClubResponse.toDto(
			club = club,
			thumbnailImageUrl = thumbnailImageUrl,
			activityImageUrls = activityImageUrls,
			clubMembers = clubMemberResponses,
			teacher = clubTeacherResponse,
			leader = clubLeaderResponse,
			applicantCount = applicantCount,
		)
	}
}
