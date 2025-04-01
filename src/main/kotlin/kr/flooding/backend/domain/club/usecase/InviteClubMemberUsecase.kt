package kr.flooding.backend.domain.club.usecase

import jakarta.transaction.Transactional
import kr.flooding.backend.domain.club.entity.ClubInvite
import kr.flooding.backend.domain.club.entity.ClubStatus
import kr.flooding.backend.domain.club.repository.ClubInviteRepository
import kr.flooding.backend.domain.club.repository.ClubRepository
import kr.flooding.backend.domain.clubMember.repository.ClubMemberRepository
import kr.flooding.backend.domain.user.repository.UserRepository
import kr.flooding.backend.global.exception.ExceptionEnum
import kr.flooding.backend.global.exception.HttpException
import kr.flooding.backend.global.exception.toPair
import kr.flooding.backend.global.thirdparty.email.EmailAdapter
import kr.flooding.backend.global.util.PasswordUtil
import kr.flooding.backend.global.util.UserUtil
import org.springframework.stereotype.Service
import java.util.UUID

@Service
@Transactional
class InviteClubMemberUsecase(
	private val userUtil: UserUtil,
	private val clubRepository: ClubRepository,
	private val clubMemberRepository: ClubMemberRepository,
	private val userRepository: UserRepository,
	private val passwordUtil: PasswordUtil,
	private val clubInviteRepository: ClubInviteRepository,
	private val emailAdapter: EmailAdapter,
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

		// 초대하는 사람이 부장이 아니라면
		if (currentUser != club.leader) {
			throw HttpException(ExceptionEnum.CLUB.NOT_CLUB_LEADER.toPair())
		}

		// 초대 받는 대상이 이미 해당 동아리의 구성원이라면
		if (clubMemberRepository.existsByClubIdAndUserId(clubId, userId)) {
			throw HttpException(ExceptionEnum.CLUB.ALREADY_JOINED_CLUB.toPair())
		}

		// 승인된 동아리가 아니라면
		if (club.status != ClubStatus.APPROVED) {
			throw HttpException(ExceptionEnum.CLUB.NOT_APPROVED_CLUB.toPair())
		}

		// 이미 해당 학생에게 초대를 보냈다면
		if (clubInviteRepository.existsById(userId)) {
			throw HttpException(ExceptionEnum.CLUB.ALREADY_SENT_INVITE.toPair())
		}

		val user =
			userRepository.findById(userId).orElseThrow {
				HttpException(ExceptionEnum.USER.NOT_FOUND_USER.toPair())
			}

		val inviteCode = passwordUtil.generateRandomCode(64)
		val clubInvite =
			ClubInvite(
				code = inviteCode,
				userId = userId,
				clubId = clubId,
			)

		clubInviteRepository.save(clubInvite)

		emailAdapter.sendInviteClub(
			email = user.email,
			inviteCode = inviteCode,
			clubName = club.name,
		)
	}
}
