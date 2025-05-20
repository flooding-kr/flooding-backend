package kr.flooding.backend.domain.massage.usecase

import kr.flooding.backend.domain.massage.persistence.entity.MassageReservation
import kr.flooding.backend.domain.massage.persistence.repository.jpa.MassageRoomJpaRepository
import kr.flooding.backend.domain.massage.persistence.repository.jpa.MassageReservationJpaRepository
import kr.flooding.backend.global.exception.ExceptionEnum
import kr.flooding.backend.global.exception.HttpException
import kr.flooding.backend.global.exception.toPair
import kr.flooding.backend.global.util.DateUtil.Companion.atEndOfDay
import kr.flooding.backend.global.util.UserUtil
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalTime
import kotlin.jvm.optionals.getOrNull

@Service
@Transactional
class ReserveMassageUsecase(
	private val massageRoomJpaRepository: MassageRoomJpaRepository,
	private val massageReservationJpaRepository: MassageReservationJpaRepository,
	private val userUtil: UserUtil,
) {
	fun execute() {
		try {
			val currentUser = userUtil.getUser()
			val currentDate = LocalDate.now()
			val currentTime = LocalTime.now()

			val startTime = LocalTime.of(20, 20)
			val endTime = LocalTime.of(21, 0)

			if (currentDate.dayOfWeek == DayOfWeek.FRIDAY ||
				currentDate.dayOfWeek == DayOfWeek.SATURDAY ||
				currentDate.dayOfWeek == DayOfWeek.SUNDAY
			) {
				throw HttpException(ExceptionEnum.MASSAGE.NO_MASSAGE_TODAY.toPair())
			}

			if (currentTime.isBefore(startTime) || currentTime.isAfter(endTime)) {
				throw HttpException(ExceptionEnum.MASSAGE.MASSAGE_OUT_OF_TIME_RANGE.toPair())
			}

			val massageReservation = massageReservationJpaRepository.findByStudent(currentUser).getOrNull()

			if (massageReservation != null && massageReservation.isCancelled) {
				throw HttpException(ExceptionEnum.MASSAGE.EXISTS_RESERVE_MASSAGE_HISTORY.toPair())
			}

			if (massageReservation != null) {
				throw HttpException(ExceptionEnum.MASSAGE.ALREADY_RESERVE_MASSAGE.toPair())
			}

			val massage =
				massageRoomJpaRepository.findByIdIsNotNull().orElseThrow {
					HttpException(ExceptionEnum.MASSAGE.NOT_FOUND_MASSAGE_ROOM.toPair())
				}

			val reservationCount = massageReservationJpaRepository.countByCreatedAtBetweenAndIsCancelled(
				currentDate.atStartOfDay(),
				currentDate.atEndOfDay(),
				isCancelled = false
			)

			if (reservationCount >= massage.reservationLimit) {
				throw HttpException(ExceptionEnum.MASSAGE.MAX_CAPACITY_MASSAGE.toPair())
			}

			massageReservationJpaRepository.save(MassageReservation(student = currentUser))
		} catch (e: DataIntegrityViolationException) {
			throw HttpException(ExceptionEnum.SELF_STUDY.ALREADY_RESERVE_SELF_STUDY.toPair())
		}
	}
}
