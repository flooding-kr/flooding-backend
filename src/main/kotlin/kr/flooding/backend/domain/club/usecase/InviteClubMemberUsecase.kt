package kr.flooding.backend.domain.club.usecase

import jakarta.transaction.Transactional
import kr.flooding.backend.domain.club.entity.ClubStatus
import kr.flooding.backend.domain.club.repository.ClubRepository
import kr.flooding.backend.domain.clubMember.entity.ClubMember
import kr.flooding.backend.domain.clubMember.repository.ClubMemberRepository
import kr.flooding.backend.domain.user.repository.UserRepository
import kr.flooding.backend.global.exception.ExceptionEnum
import kr.flooding.backend.global.exception.HttpException
import kr.flooding.backend.global.exception.toPair
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

		val user =
			userRepository
				.findById(userId)
				.orElseThrow { HttpException(ExceptionEnum.USER.NOT_FOUND_USER.toPair()) }

		if (clubMemberRepository.existsByClubIdAndUserId(clubId, userId)) {
			throw HttpException(ExceptionEnum.CLUB.ALREADY_JOINED_CLUB.toPair())
		}

		if (club.status != ClubStatus.APPROVED) {
			throw HttpException(ExceptionEnum.CLUB.NOT_APPROVED_CLUB.toPair())
		}
		clubMemberRepository.save(
			ClubMember(
				club = club,
				user = user,
			),
		)
	}
}
