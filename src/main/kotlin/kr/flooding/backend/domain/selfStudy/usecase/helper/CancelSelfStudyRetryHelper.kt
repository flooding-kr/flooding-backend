package kr.flooding.backend.domain.selfStudy.usecase.helper

import kr.flooding.backend.domain.selfStudy.persistence.repository.jdsl.SelfStudyReservationJdslRepository
import kr.flooding.backend.domain.user.persistence.entity.User
import kr.flooding.backend.global.exception.ExceptionEnum
import kr.flooding.backend.global.exception.HttpException
import kr.flooding.backend.global.exception.toPair
import kr.flooding.backend.global.util.DateUtil.Companion.atEndOfDay
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
    private val selfStudyReservationJdslRepository: SelfStudyReservationJdslRepository
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
            val currentDate = LocalDate.now()
            val prevReservation = selfStudyReservationJdslRepository.findByStudentAndCreatedAtBetweenWithPessimisticLock(
                currentUser,
                currentDate.atStartOfDay(),
                currentDate.atEndOfDay(),
            ).getOrNull()

            if (prevReservation == null || prevReservation.isCancelled) {
                throw HttpException(ExceptionEnum.SELF_STUDY.ALREADY_CANCELLED_SELF_STUDY.toPair())
            }

            prevReservation.cancelReservation()
        } catch (e: DataIntegrityViolationException) {
            throw HttpException(ExceptionEnum.SELF_STUDY.ALREADY_RESERVE_SELF_STUDY.toPair())
        } catch (e: EmptyResultDataAccessException) {
            throw HttpException(ExceptionEnum.SELF_STUDY.ALREADY_RESERVE_SELF_STUDY.toPair())
        }
    }
}
