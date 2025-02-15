package team.gsm.flooding.domain.attendance.usecase

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import team.gsm.flooding.domain.attendance.repository.AttendanceRepository
import team.gsm.flooding.domain.attendance.repository.HomebaseGroupRepository
import team.gsm.flooding.global.exception.ExceptionEnum
import team.gsm.flooding.global.exception.ExpectedException
import team.gsm.flooding.global.util.UserUtil
import java.util.UUID

@Service
@Transactional
class CancelHomebaseTableUsecase(
	private val userUtil: UserUtil,
	private val homebaseGroupRepository: HomebaseGroupRepository,
	private val attendanceRepository: AttendanceRepository,
) {
	fun execute(homebaseGroupId: UUID) {
		val currentUser = userUtil.getUser()

		val homebaseGroup =
			homebaseGroupRepository.findById(homebaseGroupId)
				.orElseThrow { ExpectedException(ExceptionEnum.NOT_FOUND_GROUP) }

		if (homebaseGroup.proposer.student != currentUser) {
			throw ExpectedException(ExceptionEnum.USER_MISMATCH)
		}

		attendanceRepository.deleteAll(homebaseGroup.participants)
		attendanceRepository.delete(homebaseGroup.proposer)
		homebaseGroupRepository.delete(homebaseGroup)
	}
}
