package kr.flooding.backend.domain.club.usecase

import jakarta.transaction.Transactional
import kr.flooding.backend.domain.club.persistence.repository.ClubRepository
import kr.flooding.backend.global.exception.ExceptionEnum
import kr.flooding.backend.global.exception.HttpException
import kr.flooding.backend.global.exception.toPair
import kr.flooding.backend.global.util.UserUtil
import org.springframework.stereotype.Service
import java.util.UUID

@Service
@Transactional
class RemoveClubUsecase(
	val clubRepository: ClubRepository,
	val userUtil: UserUtil,
) {
	fun execute(clubId: UUID) {
		val user = userUtil.getUser()

		val club =
			clubRepository.findById(clubId).orElseThrow {
				throw HttpException(ExceptionEnum.CLUB.NOT_FOUND_CLUB.toPair())
			}

		if (user != club.leader) {
			throw HttpException(ExceptionEnum.CLUB.NOT_CLUB_LEADER.toPair())
		}

		clubRepository.delete(club)
	}
}
