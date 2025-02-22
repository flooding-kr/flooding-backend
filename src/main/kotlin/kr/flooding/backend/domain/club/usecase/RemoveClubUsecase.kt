package kr.flooding.backend.domain.club.usecase

import jakarta.transaction.Transactional
import kr.flooding.backend.domain.club.repository.ClubRepository
import kr.flooding.backend.global.exception.ExceptionEnum
import kr.flooding.backend.global.exception.HttpException
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
				throw HttpException(ExceptionEnum.NOT_FOUND_CLUB)
			}

		if (user != club.leader) {
			throw HttpException(ExceptionEnum.NOT_CLUB_LEADER)
		}

		clubRepository.delete(club)
	}
}
