package kr.flooding.backend.domain.selfStudy.persistence.repository

import jakarta.persistence.LockModeType
import kr.flooding.backend.domain.selfStudy.persistence.entity.SelfStudyReservation
import kr.flooding.backend.domain.user.persistence.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import java.time.LocalDateTime
import java.util.Optional

interface SelfStudyReservationRepository : JpaRepository<SelfStudyReservation, Long> {
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	fun findByStudentAndCreatedAtBetween(
		student: User,
		createdAtBefore: LocalDateTime,
		createdAtAfter: LocalDateTime,
	): Optional<SelfStudyReservation>
}
