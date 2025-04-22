package kr.flooding.backend.domain.selfStudy.usecase.helper

import kr.flooding.backend.domain.selfStudy.persistence.entity.SelfStudyRoom
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

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
class ChangeSelfStudyLimitRetryHelper {
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
	fun execute(selfStudyRoom: SelfStudyRoom, limit: Int) {
		try {
			if(selfStudyRoom.reservationLimit != limit){
				selfStudyRoom.updateLimit(limit)
			}
		} catch (e: DataIntegrityViolationException) {
			throw HttpException(ExceptionEnum.SELF_STUDY.ALREADY_RESERVE_SELF_STUDY.toPair())
		} catch (e: EmptyResultDataAccessException) {
			throw HttpException(ExceptionEnum.SELF_STUDY.ALREADY_RESERVE_SELF_STUDY.toPair())
		}
	}
}
