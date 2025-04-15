package kr.flooding.backend.domain.selfStudy.usecase.helper

import kr.flooding.backend.domain.selfStudy.persistence.entity.SelfStudyReservation
import kr.flooding.backend.domain.selfStudy.persistence.repository.SelfStudyReservationRepository
import kr.flooding.backend.domain.selfStudy.persistence.repository.SelfStudyRoomRepository
import kr.flooding.backend.domain.user.persistence.entity.User
import kr.flooding.backend.global.exception.ExceptionEnum
import kr.flooding.backend.global.exception.HttpException
import kr.flooding.backend.global.exception.toPair
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.orm.ObjectOptimisticLockingFailureException
import org.springframework.retry.annotation.Backoff
import org.springframework.retry.annotation.Retryable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import kotlin.jvm.optionals.getOrNull

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
class ReserveSelfStudyRetryHelper(
	private val selfStudyReservationRepository: SelfStudyReservationRepository,
	private val selfStudyRoomRepository: SelfStudyRoomRepository,
) {
	@Retryable(
		retryFor = [ObjectOptimisticLockingFailureException::class],
		maxAttempts = 3,
		backoff =
			Backoff(
				delay = 30,
				maxDelay = 150,
				multiplier = 2.0,
				random = true,
			),
		notRecoverable = [HttpException::class],
	)
	fun execute(currentUser: User) {
		try {
			val prevReservation = selfStudyReservationRepository.findByStudent(currentUser).getOrNull()

			if (prevReservation != null && prevReservation.isCancelled) {
				throw HttpException(ExceptionEnum.SELF_STUDY.EXISTS_RESERVE_SELF_STUDY_HISTORY.toPair())
			}

			if (prevReservation != null) {
				throw HttpException(ExceptionEnum.SELF_STUDY.ALREADY_RESERVE_SELF_STUDY.toPair())
			}

			val selfStudyRoom = selfStudyRoomRepository.findAll().first()

			if (selfStudyRoom.reservationCount >= selfStudyRoom.reservationLimit) {
				throw HttpException(ExceptionEnum.SELF_STUDY.MAX_CAPACITY_SELF_STUDY.toPair())
			}

			selfStudyReservationRepository.save(SelfStudyReservation(student = currentUser))
			selfStudyRoom.incrementReservationCount()
		} catch (e: DataIntegrityViolationException) {
			throw HttpException(ExceptionEnum.SELF_STUDY.ALREADY_RESERVE_SELF_STUDY.toPair())
		} catch (e: EmptyResultDataAccessException) {
			throw HttpException(ExceptionEnum.SELF_STUDY.ALREADY_RESERVE_SELF_STUDY.toPair())
		}
	}
}
