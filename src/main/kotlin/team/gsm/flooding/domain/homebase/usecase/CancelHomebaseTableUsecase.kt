package team.gsm.flooding.domain.homebase.usecase

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import team.gsm.flooding.domain.attendance.repository.AttendanceRepository
import team.gsm.flooding.domain.homebase.repository.HomebaseGroupRepository
import team.gsm.flooding.global.exception.ExceptionEnum
import team.gsm.flooding.global.exception.HttpException
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
			homebaseGroupRepository
				.findById(homebaseGroupId)
				.orElseThrow { HttpException(ExceptionEnum.NOT_FOUND_GROUP) }

		if (homebaseGroup.proposer.student != currentUser) {
			throw HttpException(ExceptionEnum.USER_MISMATCH)
		}

		attendanceRepository.deleteAll(homebaseGroup.participants)
		attendanceRepository.delete(homebaseGroup.proposer)
		homebaseGroupRepository.delete(homebaseGroup)
	}
}
