package kr.flooding.backend.domain.selfStudy.usecase

import kr.flooding.backend.domain.selfStudy.usecase.helper.CancelSelfStudyRetryHelper
import kr.flooding.backend.global.exception.ExceptionEnum
import kr.flooding.backend.global.exception.HttpException
import kr.flooding.backend.global.exception.toPair
import kr.flooding.backend.global.util.UserUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalTime

@Service
@Transactional
class CancelSelfStudyUsecase(
	private val userUtil: UserUtil,
	private val cancelSelfStudyRetryHelper: CancelSelfStudyRetryHelper,
) {
	fun execute() {
		val currentUser = userUtil.getUser()
		val currentTime = LocalTime.now()

		val startTime = LocalTime.of(20, 0)
		val endTime = LocalTime.of(21, 0)

		if (currentTime.isBefore(startTime) || currentTime.isAfter(endTime)) {
			throw HttpException(ExceptionEnum.SELF_STUDY.SELF_STUDY_OUT_OF_TIME_RANGE.toPair())
		}
		cancelSelfStudyRetryHelper.execute(currentUser)
	}
}
