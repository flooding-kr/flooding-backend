package team.gsm.flooding.domain.club.usecase

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import team.gsm.flooding.domain.club.dto.response.ClubFilterResponse
import team.gsm.flooding.domain.club.dto.response.FetchClubFilterResponse
import team.gsm.flooding.domain.club.entity.ClubType
import team.gsm.flooding.domain.club.repository.ClubRepository
import team.gsm.flooding.global.util.UserUtil

@Service
@Transactional(readOnly = true)
class FetchClubFilterUsecase(
	private val clubRepository: ClubRepository,
	private val userUtil: UserUtil,
) {
	fun execute(type: ClubType): FetchClubFilterResponse {
		val clubs = clubRepository.findByType(type)
		val currentUser = userUtil.getUser()

		return FetchClubFilterResponse(clubs.map { ClubFilterResponse.toDto(it, currentUser) })
	}
}
