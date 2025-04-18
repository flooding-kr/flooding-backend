package kr.flooding.backend.domain.massage.persistence.repository.jpa

import jakarta.persistence.LockModeType
    import kr.flooding.backend.domain.massage.persistence.entity.Massage
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import java.util.Optional

interface MassageJpaRepository: JpaRepository<Massage, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    fun findByIdIsNotNull(): Optional<Massage>
}