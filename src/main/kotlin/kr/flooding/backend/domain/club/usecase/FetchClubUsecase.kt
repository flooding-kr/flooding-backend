package kr.flooding.backend.domain.club.usecase

import jakarta.transaction.Transactional
import kr.flooding.backend.domain.club.dto.response.FetchClubResponse
import kr.flooding.backend.domain.club.repository.ClubRepository
import kr.flooding.backend.domain.clubMember.repository.jpa.ClubMemberJpaRepository
import kr.flooding.backend.global.exception.ExceptionEnum
import kr.flooding.backend.global.exception.HttpException
import kr.flooding.backend.global.exception.toPair
import org.springframework.stereotype.Service
import java.util.UUID

@Service
@Transactional
class FetchClubUsecase(
	private val clubRepository: ClubRepository,
	private val clubMemberJpaRepository: ClubMemberJpaRepository,
) {
	fun execute(clubId: UUID): FetchClubResponse {
		val club =
			clubRepository
				.findWithClassroomWithTeacherById(clubId)
				.orElseThrow { HttpException(ExceptionEnum.CLUB.NOT_FOUND_CLUB.toPair()) }

		val clubMembers = clubMemberJpaRepository.findWithUserByClubId(clubId)

		return FetchClubResponse.toDto(club, clubMembers)
	}
}
