package kr.flooding.backend.domain.club.usecase

import kr.flooding.backend.domain.club.repository.ClubRepository
import kr.flooding.backend.domain.clubMember.repository.ClubMemberRepository
import kr.flooding.backend.global.exception.ExceptionEnum
import kr.flooding.backend.global.exception.HttpException
import kr.flooding.backend.global.exception.toPair
import kr.flooding.backend.global.util.UserUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
@Transactional
class RemoveClubMemberUsecase(
	val userUtil: UserUtil,
	val clubRepository: ClubRepository,
	val clubMemberRepository: ClubMemberRepository,
) {
	fun execute(
		clubId: UUID,
		userId: UUID,
	) {
		val currentUser = userUtil.getUser()
		val club =
			clubRepository.findById(clubId).orElseThrow {
				HttpException(ExceptionEnum.CLUB.NOT_FOUND_CLUB.toPair())
			}

		if (currentUser != club.leader) {
			throw HttpException(ExceptionEnum.CLUB.NOT_CLUB_LEADER.toPair())
		}

		val clubMember =
			clubMemberRepository.findByClubIdAndUserId(clubId, userId).orElseThrow {
				HttpException(ExceptionEnum.CLUB.NOT_FOUND_CLUB_MEMBER.toPair())
			}
		clubMemberRepository.delete(clubMember)
	}
}
