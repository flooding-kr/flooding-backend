package kr.flooding.backend.domain.selfStudy.usecase

import kr.flooding.backend.domain.selfStudy.repository.SelfStudyReservationRepository
import kr.flooding.backend.global.exception.ExceptionEnum
import kr.flooding.backend.global.exception.HttpException
import kr.flooding.backend.global.exception.toPair
import kr.flooding.backend.global.util.UserUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class CancelSelfStudyUsecase(
	private val userUtil: UserUtil,
	private val selfStudyReservationRepository: SelfStudyReservationRepository,
) {
	fun execute() {
		val currentUser = userUtil.getUser()
		val prevReservation =
			selfStudyReservationRepository.findByStudent(currentUser).orElseThrow {
				HttpException(ExceptionEnum.SELF_STUDY.NOT_FOUND_SELF_STUDY_RESERVATION.toPair())
			}

		prevReservation.cancelReservation()
	}
}
