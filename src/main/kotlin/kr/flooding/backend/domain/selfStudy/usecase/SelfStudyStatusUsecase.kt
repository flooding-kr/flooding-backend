package kr.flooding.backend.domain.selfStudy.usecase

import kr.flooding.backend.domain.selfStudy.dto.response.SelfStudyStatusResponse
import kr.flooding.backend.domain.selfStudy.persistence.repository.jpa.SelfStudyRoomJpaRepository
import kr.flooding.backend.global.exception.ExceptionEnum
import kr.flooding.backend.global.exception.HttpException
import kr.flooding.backend.global.exception.toPair
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

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

        if (selfStudyRoom.reservationCount >= selfStudyRoom.reservationLimit) {
            return SelfStudyStatusResponse(
                currentCount = selfStudyRoom.reservationCount,
                limit = selfStudyRoom.reservationLimit,
                isFull = true,
            )
        }

        return SelfStudyStatusResponse(
            currentCount = selfStudyRoom.reservationCount,
            limit = selfStudyRoom.reservationLimit,
        )
    }
}
