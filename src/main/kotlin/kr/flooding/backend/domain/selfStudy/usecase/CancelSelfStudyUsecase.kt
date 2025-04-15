package kr.flooding.backend.domain.selfStudy.usecase

import kr.flooding.backend.domain.selfStudy.usecase.helper.CancelSelfStudyRetryHelper
import kr.flooding.backend.global.util.UserUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class CancelSelfStudyUsecase(
	private val userUtil: UserUtil,
	private val cancelSelfStudyRetryHelper: CancelSelfStudyRetryHelper,
) {
	fun execute() {
		val currentUser = userUtil.getUser()
		cancelSelfStudyRetryHelper.execute(currentUser)
	}
}
