package kr.flooding.backend.domain.club.usecase

import jakarta.transaction.Transactional
import kr.flooding.backend.domain.club.repository.ClubRepository
import kr.flooding.backend.domain.clubMember.repository.ClubMemberRepository
import kr.flooding.backend.global.exception.ExceptionEnum
import kr.flooding.backend.global.exception.HttpException
import kr.flooding.backend.global.exception.toPair
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
		val userId = requireNotNull(user.id)

		val club =
			clubRepository.findById(clubId).orElseThrow {
				throw HttpException(ExceptionEnum.CLUB.NOT_FOUND_CLUB.toPair())
			}

		val clubMember =
			clubMemberRepository
				.findByClubIdAndUserId(clubId, userId)
				.orElseThrow { throw HttpException(ExceptionEnum.CLUB.NOT_FOUND_CLUB_MEMBER.toPair()) }

		clubMemberRepository.delete(clubMember)
	}
}
