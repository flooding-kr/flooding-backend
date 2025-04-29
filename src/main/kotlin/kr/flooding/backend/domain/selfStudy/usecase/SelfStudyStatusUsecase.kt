package kr.flooding.backend.domain.selfStudy.usecase

import kr.flooding.backend.domain.massage.enums.MassageStatus
import kr.flooding.backend.domain.selfStudy.dto.response.SelfStudyStatusResponse
import kr.flooding.backend.domain.selfStudy.persistence.repository.jpa.SelfStudyRoomJpaRepository
import kr.flooding.backend.global.exception.ExceptionEnum
import kr.flooding.backend.global.exception.HttpException
import kr.flooding.backend.global.exception.toPair
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalTime

@Service
@Transactional(readOnly = true)
class SelfStudyStatusUsecase(
    val selfStudyRoomRepository: SelfStudyRoomJpaRepository,
) {
    fun execute(): SelfStudyStatusResponse {
        val selfStudyRoom =
            selfStudyRoomRepository.findByIdIsNotNull().orElseThrow {
                HttpException(ExceptionEnum.SELF_STUDY.NOT_FOUND_SELF_STUDY_ROOM.toPair())
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

        val status =
            if (isAvailableTime &&
                isAvailableDate &&
                selfStudyRoom.reservationCount < selfStudyRoom.reservationLimit
            ) {
                MassageStatus.AVAILABLE
            } else {
                MassageStatus.UNAVAILABLE
            }

        return SelfStudyStatusResponse(
            currentCount = selfStudyRoom.reservationCount,
            limit = selfStudyRoom.reservationLimit,
            status = selfStudyRoom.reservationCount >= selfStudyRoom.reservationLimit,
        )
    }
}
