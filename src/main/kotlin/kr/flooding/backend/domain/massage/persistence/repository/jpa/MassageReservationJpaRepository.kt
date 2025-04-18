package kr.flooding.backend.domain.massage.persistence.repository.jpa

import jakarta.persistence.LockModeType
import kr.flooding.backend.domain.massage.persistence.entity.MassageReservation
import kr.flooding.backend.domain.user.persistence.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import java.util.Optional

interface MassageReservationJpaRepository : JpaRepository<MassageReservation, Long> {
	fun findByStudent(student: User): Optional<MassageReservation>

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("SELECT mr FROM MassageReservation mr WHERE mr.student = :student")
	fun findByStudentWithPessimisticLock(student: User): Optional<MassageReservation>
}
