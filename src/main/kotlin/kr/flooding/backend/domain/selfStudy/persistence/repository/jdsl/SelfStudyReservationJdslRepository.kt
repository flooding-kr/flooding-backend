package kr.flooding.backend.domain.selfStudy.persistence.repository.jdsl

import kr.flooding.backend.domain.selfStudy.persistence.entity.SelfStudyReservation
import kr.flooding.backend.domain.user.enums.Gender
import java.time.LocalDateTime

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
}
