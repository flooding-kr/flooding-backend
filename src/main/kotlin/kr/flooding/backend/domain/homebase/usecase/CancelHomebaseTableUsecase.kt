package kr.flooding.backend.domain.homebase.usecase

import kr.flooding.backend.domain.attendance.repository.AttendanceRepository
import kr.flooding.backend.domain.homebase.repository.HomebaseGroupRepository
import kr.flooding.backend.domain.homebaseParticipants.repository.HomebaseParticipantRepository
import kr.flooding.backend.global.exception.ExceptionEnum
import kr.flooding.backend.global.exception.HttpException
import kr.flooding.backend.global.exception.toPair
import kr.flooding.backend.global.util.UserUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
@Transactional
class CancelHomebaseTableUsecase(
	private val userUtil: UserUtil,
	private val homebaseGroupRepository: HomebaseGroupRepository,
	private val attendanceRepository: AttendanceRepository,
	private val homebaseParticipantRepository: HomebaseParticipantRepository,
) {
	fun execute(homebaseGroupId: UUID) {
		val currentUser = userUtil.getUser()

		val homebaseGroup =
			homebaseGroupRepository
				.findById(homebaseGroupId)
				.orElseThrow { HttpException(ExceptionEnum.CLASSROOM.NOT_FOUND_GROUP.toPair()) }

		if (homebaseGroup.proposer != currentUser) {
			throw HttpException(ExceptionEnum.USER.USER_MISMATCH.toPair())
		}

		val homebaseParticipants = homebaseParticipantRepository.findByHomebaseGroup(homebaseGroup)

		homebaseParticipantRepository.deleteAll(homebaseParticipants)
		homebaseGroupRepository.delete(homebaseGroup)
	}
}
