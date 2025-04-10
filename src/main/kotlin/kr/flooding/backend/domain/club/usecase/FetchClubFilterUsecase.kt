package kr.flooding.backend.domain.club.usecase

import kr.flooding.backend.domain.club.dto.response.ClubFilterResponse
import kr.flooding.backend.domain.club.dto.response.FetchClubFilterResponse
import kr.flooding.backend.domain.club.entity.ClubStatus
import kr.flooding.backend.domain.club.entity.ClubType
import kr.flooding.backend.domain.club.repository.ClubRepository
import kr.flooding.backend.global.util.UserUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class FetchClubFilterUsecase(
	private val clubRepository: ClubRepository,
	private val userUtil: UserUtil,
) {
	fun execute(type: ClubType?): FetchClubFilterResponse {
		val clubs =
			if (type == null) {
				clubRepository.findWithLeaderAndStatus(ClubStatus.APPROVED)
			} else {
				clubRepository.findWithLeaderByTypeAndStatus(type, ClubStatus.APPROVED)
			}

		val currentUser = userUtil.getUser()

		return FetchClubFilterResponse(clubs.map { ClubFilterResponse.toDto(it, currentUser) })
	}
}
