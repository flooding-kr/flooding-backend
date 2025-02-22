package team.gsm.flooding.domain.club.usecase

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import team.gsm.flooding.domain.club.entity.ClubStatus
import team.gsm.flooding.domain.club.repository.ClubRepository
import team.gsm.flooding.global.exception.ExceptionEnum
import team.gsm.flooding.global.exception.HttpException
import team.gsm.flooding.global.util.UserUtil
import java.util.UUID

@Service
@Transactional
class OpenClubUsecase(
	private val userUtil: UserUtil,
	private val clubRepository: ClubRepository,
) {
	fun execute(clubId: UUID) {
		val currentUser = userUtil.getUser()
		val club =
			clubRepository.findById(clubId).orElseThrow {
				HttpException(ExceptionEnum.NOT_FOUND_CLUB)
			}

		if (currentUser != club.leader) {
			throw HttpException(ExceptionEnum.NOT_CLUB_LEADER)
		}

		if (club.status != ClubStatus.APPROVED) {
			throw HttpException(ExceptionEnum.NOT_APPROVED_CLUB)
		}

		club.startRecruitment()
	}
}
