package kr.flooding.backend.domain.selfStudy.usecase

import kr.flooding.backend.domain.selfStudy.dto.web.response.SelfStudyStatusResponse
import kr.flooding.backend.domain.selfStudy.enums.SelfStudyStatus
import kr.flooding.backend.domain.selfStudy.persistence.repository.jpa.SelfStudyRoomJpaRepository
import kr.flooding.backend.domain.selfStudy.persistence.repository.jpa.SelfStudyBanJpaRepository
import kr.flooding.backend.domain.selfStudy.persistence.repository.jpa.SelfStudyReservationJpaRepository
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

        val currentTime = LocalTime.now()
        val currentDate = LocalDate.now()
        val currentUser = userUtil.getUser()

        val reservationCount = selfStudyReservationJpaRepository.countByCreatedAtBetween(
            currentDate.atStartOfDay(),
            currentDate.atEndOfDay()
        )

        val currentReservation = selfStudyReservationJpaRepository.findByStudentAndCreatedAtBetween(
            currentUser,
            currentDate.atStartOfDay(),
            currentDate.atEndOfDay(),
        )

        val startTime = LocalTime.of(20, 0)
        val endTime = LocalTime.of(21, 0)

        val isAvailableTime = currentTime.isAfter(startTime) && currentTime.isBefore(endTime)
        val isAvailableDate =
            currentDate.dayOfWeek != DayOfWeek.FRIDAY &&
            currentDate.dayOfWeek != DayOfWeek.SATURDAY &&
            currentDate.dayOfWeek != DayOfWeek.SUNDAY

        val isNotBanned = selfStudyBanJpaRepository.existsByStudent(currentUser)
        val isReserved = currentReservation.isPresent && !currentReservation.get().isCancelled
        val isAvailable =
            reservationCount < selfStudyRoom.reservationLimit &&
            isAvailableTime &&
            isAvailableDate &&
            isNotBanned &&
            currentReservation.isEmpty

        val selfStudyStatus = when {
            isReserved -> SelfStudyStatus.APPLIED
            isAvailable -> SelfStudyStatus.POSSIBLE
            else -> SelfStudyStatus.IMPOSSIBLE
        }

        return SelfStudyStatusResponse(
            currentCount = reservationCount,
            limit = selfStudyRoom.reservationLimit,
            status = selfStudyStatus,
        )
    }
}
