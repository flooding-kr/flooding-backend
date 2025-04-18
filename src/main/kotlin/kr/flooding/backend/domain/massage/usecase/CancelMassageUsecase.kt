package kr.flooding.backend.domain.massage.usecase

import kr.flooding.backend.domain.massage.persistence.repository.jpa.MassageJpaRepository
import kr.flooding.backend.domain.massage.persistence.repository.jpa.MassageReservationJpaRepository
import kr.flooding.backend.global.exception.ExceptionEnum
import kr.flooding.backend.global.exception.HttpException
import kr.flooding.backend.global.exception.toPair
import kr.flooding.backend.global.util.UserUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class CancelMassageUsecase(
	private val userUtil: UserUtil,
	private val massageReservationJpaRepository: MassageReservationJpaRepository,
	private val massageJpaRepository: MassageJpaRepository,
) {
	fun execute() {
		val prevReservation =
			massageReservationJpaRepository.findByStudentWithPessimisticLock(userUtil.getUser())
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
