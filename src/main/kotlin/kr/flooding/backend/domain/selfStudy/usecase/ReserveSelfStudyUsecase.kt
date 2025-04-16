package kr.flooding.backend.domain.selfStudy.usecase

import kr.flooding.backend.domain.selfStudy.usecase.helper.ReserveSelfStudyRetryHelper
import kr.flooding.backend.global.exception.ExceptionEnum
import kr.flooding.backend.global.exception.HttpException
import kr.flooding.backend.global.exception.toPair
import kr.flooding.backend.global.util.UserUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalTime

@Service
@Transactional
class ReserveSelfStudyUsecase(
	private val userUtil: UserUtil,
	private val reserveSelfStudyRetryHelper: ReserveSelfStudyRetryHelper,
) {
	fun execute() {
		val currentUser = userUtil.getUser()
		val currentDate = LocalDate.now()
		val currentTime = LocalTime.now()

		val startTime = LocalTime.of(20, 0)
		val endTime = LocalTime.of(21, 0)

		if (currentDate.dayOfWeek == DayOfWeek.FRIDAY ||
			currentDate.dayOfWeek == DayOfWeek.SATURDAY ||
			currentDate.dayOfWeek == DayOfWeek.SUNDAY
		) {
			throw HttpException(ExceptionEnum.SELF_STUDY.NO_SELF_STUDY_TODAY.toPair())
		}

		if (currentTime.isBefore(startTime) || currentTime.isAfter(endTime)) {
			throw HttpException(ExceptionEnum.SELF_STUDY.SELF_STUDY_OUT_OF_TIME_RANGE.toPair())
		}

		reserveSelfStudyRetryHelper.execute(currentUser)
	}
}
