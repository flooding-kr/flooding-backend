package kr.flooding.backend.domain.club.usecase

import jakarta.transaction.Transactional
import kr.flooding.backend.domain.club.dto.response.ClubStudentResponse
import kr.flooding.backend.domain.club.dto.response.ClubTeacherResponse
import kr.flooding.backend.domain.club.dto.response.FetchClubResponse
import kr.flooding.backend.domain.club.persistence.repository.ClubRepository
import kr.flooding.backend.domain.clubMember.persistence.repository.jpa.ClubMemberJpaRepository
import kr.flooding.backend.global.exception.ExceptionEnum
import kr.flooding.backend.global.exception.HttpException
import kr.flooding.backend.global.exception.toPair
import kr.flooding.backend.global.util.S3Util
import org.springframework.stereotype.Service
import java.util.UUID

@Service
@Transactional
class FetchClubUsecase(
	private val clubRepository: ClubRepository,
	private val clubMemberJpaRepository: ClubMemberJpaRepository,
	private val s3Util: S3Util,
) {
	fun execute(clubId: UUID): FetchClubResponse {
		val club =
			clubRepository
				.findWithClassroomWithTeacherById(clubId)
				.orElseThrow { HttpException(ExceptionEnum.CLUB.NOT_FOUND_CLUB.toPair()) }

		val clubMembers = clubMemberJpaRepository.findWithUserByClubIdAndUserIsNot(clubId, club.leader)

		val thumbnailImageUrl = club.thumbnailImageKey?.let { s3Util.generatePresignedUrl(it) }
		val activityImageUrls = club.activityImageKeys.map { s3Util.generatePresignedUrl(it) }

		val clubMemberResponses = clubMembers.map {
			val profileImageUrl = it.user.profileImageKey?.let {s3Util.generatePresignedUrl(it)}
			ClubStudentResponse.toDto(it.user, profileImageUrl)
		}

		val clubTeacherResponse = club.teacher?.let {
			val profileImageUrl = it.profileImageKey?.let {s3Util.generatePresignedUrl(it)}
			ClubTeacherResponse.toDto(it, profileImageUrl)
		}

		val leaderProfileImageUrl = club.leader.profileImageKey?.let { s3Util.generatePresignedUrl(it) }
		val clubLeaderResponse = ClubStudentResponse.toDto(club.leader, leaderProfileImageUrl)

		return FetchClubResponse.toDto(
			club = club,
			thumbnailImageUrl = thumbnailImageUrl,
			activityImageUrls = activityImageUrls,
			clubMembers = clubMemberResponses,
			teacher = clubTeacherResponse,
			leader = clubLeaderResponse
		)
	}
}
