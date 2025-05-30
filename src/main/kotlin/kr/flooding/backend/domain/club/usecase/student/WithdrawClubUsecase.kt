package kr.flooding.backend.domain.club.usecase.student

import jakarta.transaction.Transactional
import kr.flooding.backend.domain.club.persistence.repository.ClubRepository
import kr.flooding.backend.domain.clubMember.persistence.repository.jpa.ClubMemberJpaRepository
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
	private val clubMemberJpaRepository: ClubMemberJpaRepository,
) {
	fun execute(clubId: UUID) {
		val user = userUtil.getUser()
		val userId = requireNotNull(user.id)

		val club =
			clubRepository.findById(clubId).orElseThrow {
				throw HttpException(ExceptionEnum.CLUB.NOT_FOUND_CLUB.toPair())
			}

		if(user == club.leader){
			throw HttpException(ExceptionEnum.CLUB.LEADER_CANNOT_WITHDRAW.toPair())
		}

		val clubMember =
			clubMemberJpaRepository
				.findByClubIdAndUserId(clubId, userId)
				.orElseThrow { throw HttpException(ExceptionEnum.CLUB.NOT_FOUND_CLUB_MEMBER.toPair()) }

		clubMemberJpaRepository.delete(clubMember)
	}
}
