package kr.flooding.backend.domain.selfStudy.usecase

import kr.flooding.backend.domain.selfStudy.persistence.entity.SelfStudyReservation
import kr.flooding.backend.domain.selfStudy.persistence.repository.jdsl.SelfStudyReservationJdslRepository
import kr.flooding.backend.domain.selfStudy.persistence.repository.jpa.SelfStudyBanJpaRepository
import kr.flooding.backend.domain.selfStudy.persistence.repository.jpa.SelfStudyReservationJpaRepository
import kr.flooding.backend.domain.selfStudy.persistence.repository.jpa.SelfStudyRoomJpaRepository
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
import kotlin.jvm.optionals.getOrNull

@Service
@Transactional
class ReserveSelfStudyUsecase(
	private val userUtil: UserUtil,
	private val selfStudyBanJpaRepository: SelfStudyBanJpaRepository,
	private val selfStudyReservationJpaRepository: SelfStudyReservationJpaRepository,
	private val selfStudyReservationJdslRepository: SelfStudyReservationJdslRepository,
	private val selfStudyRoomRepository: SelfStudyRoomJpaRepository,
) {
	fun execute() {
		val currentUser = userUtil.getUser()
		val currentDate = LocalDate.now()
		val currentTime = LocalTime.now()

		val isValidDayOfWeek =
			currentDate.dayOfWeek == DayOfWeek.FRIDAY ||
					currentDate.dayOfWeek == DayOfWeek.SATURDAY ||
					currentDate.dayOfWeek == DayOfWeek.SUNDAY

		if (isValidDayOfWeek) {
			throw HttpException(ExceptionEnum.SELF_STUDY.NO_SELF_STUDY_TODAY.toPair())
		}

		val startTime = LocalTime.of(20, 0)
		val endTime = LocalTime.of(21, 0)
		val isValidTime = currentTime.isBefore(startTime) || currentTime.isAfter(endTime)

		if (isValidTime) {
			throw HttpException(ExceptionEnum.SELF_STUDY.SELF_STUDY_OUT_OF_TIME_RANGE.toPair())
		}

		val isExistsSelfStudyBan = selfStudyBanJpaRepository.existsByStudent(currentUser)
		if (isExistsSelfStudyBan) {
			throw HttpException(ExceptionEnum.SELF_STUDY.ALREADY_BANNED_SELF_STUDY.toPair())
		}

		val prevReservation = selfStudyReservationJdslRepository.findByStudentAndCreatedAtBetweenWithPessimisticLock(
			currentUser,
			currentDate.atStartOfDay(),
			currentDate.atEndOfDay(),
		).getOrNull()

		if (prevReservation != null && prevReservation.isCancelled) {
			throw HttpException(ExceptionEnum.SELF_STUDY.EXISTS_RESERVE_SELF_STUDY_HISTORY.toPair())
		}

		if (prevReservation != null) {
			throw HttpException(ExceptionEnum.SELF_STUDY.ALREADY_RESERVE_SELF_STUDY.toPair())
		}

		val selfStudyRoom = selfStudyRoomRepository.findByIdIsNotNull().orElseThrow {
			HttpException(ExceptionEnum.SELF_STUDY.NOT_FOUND_SELF_STUDY_ROOM.toPair())
		}

		val reservationCount = selfStudyReservationJpaRepository.countByCreatedAtBetweenAndIsCancelled(
			currentDate.atStartOfDay(),
			currentDate.atEndOfDay(),
			false
		)

		if (reservationCount >= selfStudyRoom.reservationLimit) {
			throw HttpException(ExceptionEnum.SELF_STUDY.MAX_CAPACITY_SELF_STUDY.toPair())
		}

		selfStudyReservationJpaRepository.save(
			SelfStudyReservation(
				student = currentUser
			)
		)
	}
}
