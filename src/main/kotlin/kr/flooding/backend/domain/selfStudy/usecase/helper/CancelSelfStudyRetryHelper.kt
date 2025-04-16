package kr.flooding.backend.domain.selfStudy.usecase.helper

import kr.flooding.backend.domain.selfStudy.persistence.repository.SelfStudyReservationRepository
import kr.flooding.backend.domain.selfStudy.persistence.repository.SelfStudyRoomRepository
import kr.flooding.backend.domain.user.persistence.entity.User
import kr.flooding.backend.global.exception.ExceptionEnum
import kr.flooding.backend.global.exception.HttpException
import kr.flooding.backend.global.exception.toPair
import kr.flooding.backend.global.util.TodayUtil
import kr.flooding.backend.global.util.TodayUtil.Companion.getAtEndOfToday
import kr.flooding.backend.global.util.TodayUtil.Companion.getAtStartOfToday
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.orm.ObjectOptimisticLockingFailureException
import org.springframework.retry.annotation.Backoff
import org.springframework.retry.annotation.Retryable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import kotlin.jvm.optionals.getOrNull

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
class CancelSelfStudyRetryHelper(
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
			val prevReservation =
				selfStudyReservationRepository
					.findByStudentAndCreatedAtBetween(
						currentUser,
						getAtStartOfToday(),
						getAtEndOfToday(),
					).getOrNull()

			if (prevReservation == null || prevReservation.isCancelled) {
				throw HttpException(ExceptionEnum.SELF_STUDY.ALREADY_CANCELLED_SELF_STUDY.toPair())
			}

			val selfStudyRoom =
				selfStudyRoomRepository.findByIdIsNotNull().orElseThrow {
					throw HttpException(ExceptionEnum.SELF_STUDY.NOT_FOUND_SELF_STUDY_ROOM.toPair())
				}

			prevReservation.cancelReservation()
			selfStudyRoom.decrementReservationCount()
		} catch (e: DataIntegrityViolationException) {
			throw HttpException(ExceptionEnum.SELF_STUDY.ALREADY_RESERVE_SELF_STUDY.toPair())
		} catch (e: EmptyResultDataAccessException) {
			throw HttpException(ExceptionEnum.SELF_STUDY.ALREADY_RESERVE_SELF_STUDY.toPair())
		}
	}
}
