package team.gsm.flooding.domain.club.usecase

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import team.gsm.flooding.domain.club.repository.ClubRepository
import team.gsm.flooding.domain.clubMember.repository.ClubMemberRepository
import team.gsm.flooding.global.exception.ExceptionEnum
import team.gsm.flooding.global.exception.HttpException
import team.gsm.flooding.global.util.UserUtil
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
				HttpException(ExceptionEnum.NOT_FOUND_CLUB)
			}

		if (currentUser != club.leader) {
			throw HttpException(ExceptionEnum.NOT_CLUB_LEADER)
		}

		val clubMember =
			clubMemberRepository.findByClubIdAndUserId(clubId, userId).orElseThrow {
				HttpException(ExceptionEnum.NOT_FOUND_CLUB_MEMBER)
			}
		clubMemberRepository.delete(clubMember)
	}
}
