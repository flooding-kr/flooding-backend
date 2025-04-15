package kr.flooding.backend.domain.selfStudy.repository

import kr.flooding.backend.domain.selfStudy.entity.SelfStudyReservation
import kr.flooding.backend.domain.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface SelfStudyReservationRepository : JpaRepository<SelfStudyReservation, Long> {
	fun findByStudent(user: User): Optional<SelfStudyReservation>
}
