package kr.flooding.backend.domain.homebase.usecase

import kr.flooding.backend.domain.attendance.repository.AttendanceRepository
import kr.flooding.backend.domain.homebase.repository.HomebaseGroupRepository
import kr.flooding.backend.global.exception.ExceptionEnum
import kr.flooding.backend.global.exception.HttpException
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
