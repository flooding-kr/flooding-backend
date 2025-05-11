package kr.flooding.backend.domain.selfStudy.usecase

import kr.flooding.backend.domain.selfStudy.persistence.repository.jpa.SelfStudyReservationJpaRepository
import kr.flooding.backend.global.exception.ExceptionEnum
import kr.flooding.backend.global.exception.HttpException
import kr.flooding.backend.global.exception.toPair
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
@Transactional
class AbsenceSelfStudyUsecase(
    private val selfStudyReservationJpaRepository: SelfStudyReservationJpaRepository,
) {
    fun execute(selfStudyReservationId: UUID) {
        val reservation = selfStudyReservationJpaRepository.findById(selfStudyReservationId).orElseThrow {
            HttpException(ExceptionEnum.SELF_STUDY.NOT_FOUND_SELF_STUDY_RESERVATION.toPair())
        }

        reservation.absenceSelfStudy()
    }
}