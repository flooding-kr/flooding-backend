package kr.flooding.backend.domain.selfStudy.usecase

import jakarta.transaction.Transactional
import kr.flooding.backend.domain.selfStudy.persistence.entity.SelfStudyBan
import kr.flooding.backend.domain.selfStudy.persistence.repository.jpa.SelfStudyBanJpaRepository
import kr.flooding.backend.domain.selfStudy.persistence.repository.jpa.SelfStudyReservationJpaRepository
import kr.flooding.backend.global.util.DateUtil.Companion.atEndOfDay
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
@Transactional
class BanForSelfStudyAbsenceUsecase(
    private val selfStudyReservationJpaRepository: SelfStudyReservationJpaRepository,
    private val selfStudyBanJpaRepository: SelfStudyBanJpaRepository
) {
    fun execute() {
        val currentDate = LocalDate.now()

        val selfStudyAbsences = selfStudyReservationJpaRepository.findByCreatedAtBetweenAndIsCancelledAndIsPresent(
            currentDate.atStartOfDay(),
            currentDate.atEndOfDay(),
            false,
            false
        )

        val selfStudyBanForAbsences = selfStudyAbsences.map {
            SelfStudyBan(
                student = it.student,
                resumeDate = LocalDate.now().plusDays(7)
            )
        }

        selfStudyBanJpaRepository.saveAll(selfStudyBanForAbsences)
    }
}