package kr.flooding.backend.domain.selfStudy.persistence.repository.jdsl

import kr.flooding.backend.domain.selfStudy.persistence.entity.SelfStudyReservation
import kr.flooding.backend.domain.user.enums.Gender
import kr.flooding.backend.domain.user.persistence.entity.User
import java.time.LocalDateTime
import java.util.*

interface SelfStudyReservationJdslRepository {
    fun findByCreatedByBetweenAndYearAndClassroomAndGenderAndNameLikesAndIsCancelled(
        createdAtBefore: LocalDateTime,
        createdAtAfter: LocalDateTime,
        year: Int?,
        classroom: Int?,
        gender: Gender?,
        name: String?,
        isCancelled: Boolean
    ): List<SelfStudyReservation>

    fun findByStudentAndCreatedAtBetweenWithPessimisticLock(
        student: User,
        createdAtBefore: LocalDateTime,
        createdAtAfter: LocalDateTime,
    ): Optional<SelfStudyReservation>
}
