package kr.flooding.backend.domain.selfStudy.usecase

import kr.flooding.backend.domain.selfStudy.persistence.entity.SelfStudyBan
import kr.flooding.backend.domain.selfStudy.persistence.repository.jpa.SelfStudyReservationJpaRepository
import kr.flooding.backend.domain.selfStudy.persistence.repository.jpa.SelfStudyBanJpaRepository
import kr.flooding.backend.global.exception.ExceptionEnum
import kr.flooding.backend.global.exception.HttpException
import kr.flooding.backend.global.exception.toPair
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.util.UUID

@Service
@Transactional
class BanSelfStudyUsecase(
    private val selfStudyReservationJpaRepository: SelfStudyReservationJpaRepository,
    private val selfStudyBanJpaRepository: SelfStudyBanJpaRepository,
) {
    fun execute(selfStudyReservationId: UUID) {
        val selfStudyReservation = selfStudyReservationJpaRepository.findById(selfStudyReservationId).orElseThrow {
            HttpException(ExceptionEnum.SELF_STUDY.NOT_FOUND_SELF_STUDY_RESERVATION.toPair())
        }

        if (selfStudyBanJpaRepository.existsByStudent(selfStudyReservation.student)) {
            throw HttpException(ExceptionEnum.SELF_STUDY.ALREADY_BANNED_SELF_STUDY.toPair())
        }

        selfStudyBanJpaRepository.save(
            SelfStudyBan(
                student = selfStudyReservation.student,
                resumeDate = LocalDate.now().plusDays(7)
            )
        )
    }
}