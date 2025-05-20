package kr.flooding.backend.domain.massage.usecase

import kr.flooding.backend.domain.massage.dto.response.FetchMassageResponse
import kr.flooding.backend.domain.massage.enums.MassageStatus
import kr.flooding.backend.domain.massage.persistence.repository.jpa.MassageReservationJpaRepository
import kr.flooding.backend.domain.massage.persistence.repository.jpa.MassageRoomJpaRepository
import kr.flooding.backend.global.exception.ExceptionEnum
import kr.flooding.backend.global.exception.HttpException
import kr.flooding.backend.global.exception.toPair
import kr.flooding.backend.global.util.DateUtil.Companion.atEndOfDay
import kr.flooding.backend.global.util.UserUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalTime

@Service
@Transactional
class FetchMassageStatusUsecase(
	private val massageRoomJpaRepository: MassageRoomJpaRepository,
	private val massageReservationJpaRepository: MassageReservationJpaRepository,
	private val userUtil: UserUtil
) {
	fun execute(): FetchMassageResponse {
		val massageRoom = massageRoomJpaRepository.findByIdIsNotNull().orElseThrow {
			HttpException(ExceptionEnum.MASSAGE.NOT_FOUND_MASSAGE_ROOM.toPair())
		}

		val currentUser = userUtil.getUser()
		val currentDate = LocalDate.now()
		val currentTime = LocalTime.now()

		val currentReservation = massageReservationJpaRepository.findByStudentAndCreatedAtBetween(
			currentUser,
			currentDate.atStartOfDay(),
			currentDate.atEndOfDay()
		)

		val reservationCount = massageReservationJpaRepository.countByCreatedAtBetween(
			currentDate.atStartOfDay(),
			currentDate.atEndOfDay()
		)

		val startTime = LocalTime.of(20, 20)
		val endTime = LocalTime.of(21, 0)

		val isAvailableTime = currentTime.isAfter(startTime) && currentTime.isBefore(endTime)
		val isAvailableDate =
			currentDate.dayOfWeek != DayOfWeek.FRIDAY &&
			currentDate.dayOfWeek != DayOfWeek.SATURDAY &&
			currentDate.dayOfWeek != DayOfWeek.SUNDAY

		val isReserved = currentReservation.isPresent && !currentReservation.get().isCancelled
		val isAvailable =
			isAvailableTime &&
			isAvailableDate &&
			reservationCount < massageRoom.reservationLimit &&
			currentReservation.isEmpty

		val massageStatus = when {
			isReserved -> MassageStatus.APPLIED
			isAvailable -> MassageStatus.POSSIBLE
			else -> MassageStatus.IMPOSSIBLE
		}

		return FetchMassageResponse(
			currentCount = reservationCount,
			limit = massageRoom.reservationLimit,
			status = massageStatus,
		)
	}
}
