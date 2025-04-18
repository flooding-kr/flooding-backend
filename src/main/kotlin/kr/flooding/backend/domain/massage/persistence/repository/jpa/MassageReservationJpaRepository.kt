package kr.flooding.backend.domain.massage.persistence.repository.jpa

import kr.flooding.backend.domain.massage.persistence.entity.MassageReservation
import kr.flooding.backend.domain.user.persistence.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface MassageReservationJpaRepository : JpaRepository<MassageReservation, Long> {
	fun findByStudent(student: User): Optional<MassageReservation>
}
