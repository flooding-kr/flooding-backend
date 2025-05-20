package kr.flooding.backend.domain.selfStudy.persistence.repository.jpa

import kr.flooding.backend.domain.selfStudy.persistence.entity.SelfStudyReservation
import kr.flooding.backend.domain.user.persistence.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime
import java.util.Optional
import java.util.UUID

interface SelfStudyReservationJpaRepository : JpaRepository<SelfStudyReservation, UUID> {
    fun findByStudentAndCreatedAtBetween(
        student: User,
        createdAtBefore: LocalDateTime,
        createdAtAfter: LocalDateTime,
    ): Optional<SelfStudyReservation>

    fun countByCreatedAtBetweenAndIsCancelled(
        createdAtBefore: LocalDateTime,
        createdAtAfter: LocalDateTime,
        isCancelled: Boolean
    ): Int
}
