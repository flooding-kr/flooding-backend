package kr.flooding.backend.domain.massage.usecase

import kr.flooding.backend.domain.massage.persistence.repository.jpa.MassageJpaRepository
import kr.flooding.backend.domain.massage.persistence.repository.jpa.MassageReservationJpaRepository
import kr.flooding.backend.global.exception.ExceptionEnum
import kr.flooding.backend.global.exception.HttpException
import kr.flooding.backend.global.exception.toPair
import kr.flooding.backend.global.util.UserUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalTime

@Service
@Transactional
class CancelMassageUsecase(
	private val userUtil: UserUtil,
	private val massageReservationJpaRepository: MassageReservationJpaRepository,
	private val massageJpaRepository: MassageJpaRepository,
) {
	fun execute() {
		val currentUser = userUtil.getUser()
		val currentTime = LocalTime.now()

		val startTime = LocalTime.of(20, 20)
		val endTime = LocalTime.of(21, 0)

		if (currentTime.isBefore(startTime) || currentTime.isAfter(endTime)) {
			throw HttpException(ExceptionEnum.MASSAGE.MASSAGE_OUT_OF_TIME_RANGE.toPair())
		}

		val prevReservation =
			massageReservationJpaRepository.findByStudentWithPessimisticLock(currentUser)
				.orElseThrow { HttpException(ExceptionEnum.MASSAGE.NOT_FOUND_MASSAGE_RESERVATION.toPair()) }

		if (prevReservation.isCancelled) {
			throw HttpException(ExceptionEnum.MASSAGE.ALREADY_CANCELLED_MASSAGE.toPair())
		}

		val massage =
			massageJpaRepository.findByIdIsNotNull()
				.orElseThrow { HttpException(ExceptionEnum.MASSAGE.NOT_FOUND_MASSAGE_ROOM.toPair()) }

		prevReservation.cancelReservation()
		massage.decrementReservationCount()
	}
}
