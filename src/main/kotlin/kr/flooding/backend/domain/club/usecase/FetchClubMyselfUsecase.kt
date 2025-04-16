package kr.flooding.backend.domain.club.usecase

import kr.flooding.backend.domain.club.dto.response.ClubMyselfResponse
import kr.flooding.backend.domain.club.dto.response.FetchClubMyselfResponse
import kr.flooding.backend.domain.clubMember.persistence.repository.jdsl.ClubMemberJdslRepository
import kr.flooding.backend.global.util.UserUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class FetchClubMyselfUsecase(
	private val clubMemberJdslRepository: ClubMemberJdslRepository,
	private val userUtil: UserUtil,
) {
	fun execute(): FetchClubMyselfResponse {
		val currentUser = userUtil.getUser()
		val currentClubMember = clubMemberJdslRepository.findByUser(currentUser)
		val clubs = currentClubMember.map { it.club }

		return FetchClubMyselfResponse(clubs.map { ClubMyselfResponse.toDto(it, currentUser) })
	}
}
