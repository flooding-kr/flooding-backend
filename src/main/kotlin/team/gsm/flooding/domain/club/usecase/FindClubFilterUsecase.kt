package team.gsm.flooding.domain.club.usecase

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import team.gsm.flooding.domain.club.dto.response.FindClubFilterResponse
import team.gsm.flooding.domain.club.entity.ClubStatus
import team.gsm.flooding.domain.club.repository.ClubRepository
import team.gsm.flooding.global.util.UserUtil

@Service
@Transactional(readOnly = true)
class FindClubFilterUsecase(
	private val clubRepository: ClubRepository,
	private val userUtil: UserUtil,
) {
	fun execute(): FindClubFilterResponse {
		val clubs = clubRepository.findAll()
		val currentUser = userUtil.getUser()
		val myClubs =
			clubs.filter { club ->
				club.leader == currentUser
			}
		val otherClubs =
			clubs
				.filter { club ->
					(club.leader != currentUser) && (club.status != ClubStatus.PENDING)
				}
				.groupBy { it.type }

		return FindClubFilterResponse.toDto(myClubs, otherClubs)
	}
}
