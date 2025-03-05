package kr.flooding.backend.domain.club.usecase

import jakarta.transaction.Transactional
import kr.flooding.backend.domain.club.dto.response.FetchClubResponse
import kr.flooding.backend.domain.club.repository.ClubRepository
import kr.flooding.backend.domain.clubMember.repository.ClubMemberRepository
import kr.flooding.backend.global.exception.ExceptionEnum
import kr.flooding.backend.global.exception.HttpException
import kr.flooding.backend.global.exception.toPair
import org.springframework.stereotype.Service
import java.util.UUID

@Service
@Transactional
class FetchClubUsecase(
	private val clubRepository: ClubRepository,
	private val clubMemberRepository: ClubMemberRepository,
) {
	fun execute(clubId: UUID): FetchClubResponse {
		val club =
			clubRepository
				.findById(clubId)
				.orElseThrow { HttpException(ExceptionEnum.CLUB.NOT_FOUND_CLUB.toPair()) }

		val clubMembers = clubMemberRepository.findByClubId(clubId)

		return FetchClubResponse.toDto(club, clubMembers)
	}
}
