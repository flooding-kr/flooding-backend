package kr.flooding.backend.domain.club.usecase

import jakarta.transaction.Transactional
import kr.flooding.backend.domain.club.persistence.repository.ClubInviteRepository
import kr.flooding.backend.domain.club.persistence.repository.ClubRepository
import kr.flooding.backend.domain.clubMember.persistence.entity.ClubMember
import kr.flooding.backend.domain.clubMember.persistence.repository.jpa.ClubMemberJpaRepository
import kr.flooding.backend.global.exception.ExceptionEnum
import kr.flooding.backend.global.exception.HttpException
import kr.flooding.backend.global.exception.toPair
import kr.flooding.backend.global.util.UserUtil
import org.springframework.stereotype.Service

@Service
@Transactional
class ConfirmClubInviteUsecase(
	private val userUtil: UserUtil,
	private val clubRepository: ClubRepository,
	private val clubMemberJpaRepository: ClubMemberJpaRepository,
	private val clubInviteRepository: ClubInviteRepository,
) {
	fun execute(inviteCode: String) {
		val currentUser = userUtil.getUser()
		val userId = requireNotNull(currentUser.id)

		val clubInvite =
			clubInviteRepository.findById(userId).orElseThrow {
				HttpException(ExceptionEnum.CLUB.NOT_FOUND_CLUB_INVITE.toPair())
			}

		if (inviteCode != clubInvite.code) {
			throw HttpException(ExceptionEnum.CLUB.INVALID_CLUB_INVITE_CODE.toPair())
		}

		val club =
			clubRepository.findById(clubInvite.clubId).orElseThrow {
				HttpException(ExceptionEnum.CLUB.NOT_FOUND_CLUB.toPair())
			}

		val newClubMember =
			ClubMember(
				club = club,
				user = currentUser,
			)

		clubMemberJpaRepository.save(newClubMember)
		clubInviteRepository.deleteById(userId)
	}
}
