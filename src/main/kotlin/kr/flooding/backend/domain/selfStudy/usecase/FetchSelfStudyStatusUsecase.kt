package kr.flooding.backend.domain.selfStudy.usecase

import kr.flooding.backend.domain.selfStudy.dto.web.response.SelfStudyStatusResponse
import kr.flooding.backend.domain.selfStudy.enums.SelfStudyStatus
import kr.flooding.backend.domain.selfStudy.persistence.repository.jpa.SelfStudyRoomJpaRepository
import kr.flooding.backend.domain.selfStudy.persistence.repository.jpa.SelfStudyBanJpaRepository
import kr.flooding.backend.domain.selfStudy.persistence.repository.jpa.SelfStudyReservationJpaRepository
import kr.flooding.backend.global.exception.ExceptionEnum
import kr.flooding.backend.global.exception.HttpException
import kr.flooding.backend.global.exception.toPair
import kr.flooding.backend.global.util.DateUtil.Companion.getAtEndOfDay
import kr.flooding.backend.global.util.DateUtil.Companion.getAtStartOfDay
import kr.flooding.backend.global.util.UserUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.LocalTime

@Service
@Transactional(readOnly = true)
class FetchSelfStudyStatusUsecase(
    val userUtil: UserUtil,
    val selfStudyRoomRepository: SelfStudyRoomJpaRepository,
    val selfStudyBanJpaRepository: SelfStudyBanJpaRepository,
    val selfStudyReservationJpaRepository: SelfStudyReservationJpaRepository
) {
    fun execute(): SelfStudyStatusResponse {
        val selfStudyRoom =
            selfStudyRoomRepository.findByIdIsNotNull().orElseThrow {
                HttpException(ExceptionEnum.SELF_STUDY.NOT_FOUND_SELF_STUDY_ROOM.toPair())
            }

        val currentDateTime = LocalDateTime.now()
        val currentTime = currentDateTime.toLocalTime()
        val currentUser = userUtil.getUser()

        val currentReservation = selfStudyReservationJpaRepository.findByStudentAndCreatedAtBetween(
            currentUser,
            currentDateTime.getAtStartOfDay(),
            currentDateTime.getAtEndOfDay(),
        )

        val isAlreadyReserved = currentReservation.isPresent && !currentReservation.get().isCancelled

        val startTime = LocalTime.of(20, 0)
        val endTime = LocalTime.of(21, 0)

        val isAvailableTime = currentTime.isAfter(startTime) && currentTime.isBefore(endTime)
        val isAvailableDate =
            currentDateTime.dayOfWeek != DayOfWeek.FRIDAY &&
            currentDateTime.dayOfWeek != DayOfWeek.SATURDAY &&
            currentDateTime.dayOfWeek != DayOfWeek.SUNDAY
        
        val isAvailableDateTime = isAvailableTime && isAvailableDate
        val isLeftSeat = selfStudyRoom.reservationCount < selfStudyRoom.reservationLimit
        val isBanned = selfStudyBanJpaRepository.existsByStudent(currentUser)

        val selfStudyStatus = when {
            isAlreadyReserved -> SelfStudyStatus.APPLIED
            isAvailableDateTime && isLeftSeat && !isBanned -> SelfStudyStatus.POSSIBLE
            else -> SelfStudyStatus.IMPOSSIBLE
        }

        return SelfStudyStatusResponse(
            currentCount = selfStudyRoom.reservationCount,
            limit = selfStudyRoom.reservationLimit,
            status = selfStudyStatus,
        )
    }
}
