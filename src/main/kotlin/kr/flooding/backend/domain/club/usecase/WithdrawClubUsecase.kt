package kr.flooding.backend.domain.club.usecase

import jakarta.transaction.Transactional
import kr.flooding.backend.domain.club.repository.ClubRepository
import kr.flooding.backend.domain.clubMember.repository.ClubMemberRepository
import kr.flooding.backend.global.exception.ExceptionEnum
import kr.flooding.backend.global.exception.HttpException
import kr.flooding.backend.global.util.UserUtil
import org.springframework.stereotype.Service
import java.util.UUID

@Service
@Transactional
class WithdrawClubUsecase(
	private val clubRepository: ClubRepository,
	private val userUtil: UserUtil,
	private val clubMemberRepository: ClubMemberRepository,
) {
	fun execute(clubId: UUID) {
		val user = userUtil.getUser()

		val club =
			clubRepository.findById(clubId).orElseThrow {
				throw HttpException(ExceptionEnum.NOT_FOUND_CLUB)
			}

		val clubMember =
			clubMemberRepository
				.findByClubIdAndUserId(club.id, user.id)
				.orElseThrow { throw HttpException(ExceptionEnum.NOT_FOUND_CLUB_MEMBER) }

		clubMemberRepository.delete(clubMember)
	}
}
