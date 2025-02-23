package kr.flooding.backend.domain.club.usecase

import kr.flooding.backend.domain.club.entity.ClubStatus
import kr.flooding.backend.domain.club.repository.ClubRepository
import kr.flooding.backend.global.exception.ExceptionEnum
import kr.flooding.backend.global.exception.HttpException
import kr.flooding.backend.global.util.UserUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
@Transactional
class CloseClubUsecase(
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

		club.stopRecruitment()
	}
}
