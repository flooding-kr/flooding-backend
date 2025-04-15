package kr.flooding.backend.domain.selfStudy.persistence.repository

import kr.flooding.backend.domain.selfStudy.persistence.entity.SelfStudyReservation
import kr.flooding.backend.domain.user.persistence.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDate
import java.util.Optional

interface SelfStudyReservationRepository : JpaRepository<SelfStudyReservation, Long> {
	fun findByStudentAndCreatedAt(
		user: User,
		createdAt: LocalDate,
	): Optional<SelfStudyReservation>
}
