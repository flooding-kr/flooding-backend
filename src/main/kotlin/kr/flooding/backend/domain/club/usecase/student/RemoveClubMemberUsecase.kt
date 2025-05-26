package kr.flooding.backend.domain.club.usecase.student

import kr.flooding.backend.domain.club.persistence.repository.ClubRepository
import kr.flooding.backend.domain.clubMember.persistence.repository.jpa.ClubMemberJpaRepository
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
	val clubMemberJpaRepository: ClubMemberJpaRepository,
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
			clubMemberJpaRepository.findByClubIdAndUserId(clubId, userId).orElseThrow {
				HttpException(ExceptionEnum.CLUB.NOT_FOUND_CLUB_MEMBER.toPair())
			}

		if(clubMember.user == club.leader){
			throw HttpException(ExceptionEnum.CLUB.LEADER_CANNOT_WITHDRAW.toPair())
		}

		clubMemberJpaRepository.delete(clubMember)
	}
}
