package kr.flooding.backend.domain.massage.usecase

import kr.flooding.backend.domain.massage.dto.response.FetchMassageResponse
import kr.flooding.backend.domain.massage.persistence.repository.jpa.MassageRoomJpaRepository
import kr.flooding.backend.global.exception.ExceptionEnum
import kr.flooding.backend.global.exception.HttpException
import kr.flooding.backend.global.exception.toPair
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalTime

@Service
@Transactional
class FetchMassageUsecase(
	private val massageRoomJpaRepository: MassageRoomJpaRepository,
) {
	fun execute(): FetchMassageResponse {
		val massageRoom = massageRoomJpaRepository.findByIdIsNotNull().orElseThrow {
			HttpException(ExceptionEnum.MASSAGE.NOT_FOUND_MASSAGE_ROOM.toPair())
		}

		val currentDate = LocalDate.now()
		val currentTime = LocalTime.now()

		val startTime = LocalTime.of(20, 20)
		val endTime = LocalTime.of(21, 0)

		val isAvailableTime = currentTime.isAfter(startTime) && currentTime.isBefore(endTime)
		val isAvailableDate =
			currentDate.dayOfWeek != DayOfWeek.FRIDAY &&
			currentDate.dayOfWeek != DayOfWeek.SATURDAY &&
			currentDate.dayOfWeek != DayOfWeek.SUNDAY

		val isAvailable = isAvailableTime && isAvailableDate &&
			massageRoom.reservationCount < massageRoom.reservationLimit

		return FetchMassageResponse(
			count = massageRoom.reservationCount,
			limit = massageRoom.reservationLimit,
			isAvailable = isAvailable,
		)
	}
}
