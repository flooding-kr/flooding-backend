package kr.flooding.backend.domain.selfStudy.usecase

import kr.flooding.backend.domain.selfStudy.dto.ChangeSelfStudyLimitRequest
import kr.flooding.backend.domain.selfStudy.persistence.repository.SelfStudyRoomRepository
import kr.flooding.backend.domain.selfStudy.usecase.helper.CancelSelfStudyRetryHelper
import kr.flooding.backend.domain.selfStudy.usecase.helper.ChangeSelfStudyLimitRetryHelper
import kr.flooding.backend.global.exception.ExceptionEnum
import kr.flooding.backend.global.exception.HttpException
import kr.flooding.backend.global.exception.toPair
import kr.flooding.backend.global.util.UserUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalTime

@Service
@Transactional
class ChangeSelfStudyLimitUsecase(
	private val selfStudyRoomRepository: SelfStudyRoomRepository,
	private val changeSelfStudyLimitRetryHelper: ChangeSelfStudyLimitRetryHelper
) {
	fun execute(request: ChangeSelfStudyLimitRequest) {
		val selfStudyRoom = selfStudyRoomRepository.findByIdIsNotNull().orElseThrow {
			HttpException(ExceptionEnum.SELF_STUDY.NOT_FOUND_SELF_STUDY_ROOM.toPair())
		}

		if(selfStudyRoom.reservationLimit != request.limit){
			changeSelfStudyLimitRetryHelper.execute(selfStudyRoom, request.limit)
		}
	}
}
