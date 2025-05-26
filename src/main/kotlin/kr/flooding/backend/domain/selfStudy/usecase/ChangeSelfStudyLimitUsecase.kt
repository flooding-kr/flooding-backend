package kr.flooding.backend.domain.selfStudy.usecase

import kr.flooding.backend.domain.selfStudy.dto.web.request.ChangeSelfStudyLimitRequest
import kr.flooding.backend.domain.selfStudy.persistence.repository.jpa.SelfStudyRoomJpaRepository
import kr.flooding.backend.global.exception.ExceptionEnum
import kr.flooding.backend.global.exception.HttpException
import kr.flooding.backend.global.exception.toPair
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class ChangeSelfStudyLimitUsecase(
    private val selfStudyRoomRepository: SelfStudyRoomJpaRepository,
) {
    fun execute(request: ChangeSelfStudyLimitRequest) {
        val selfStudyRoom =
            selfStudyRoomRepository.findByIdIsNotNull().orElseThrow {
                HttpException(ExceptionEnum.SELF_STUDY.NOT_FOUND_SELF_STUDY_ROOM.toPair())
            }

        if (selfStudyRoom.reservationLimit != request.limit) {
            selfStudyRoom.updateLimit(request.limit)
        }
    }
}
