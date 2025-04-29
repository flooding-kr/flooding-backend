package kr.flooding.backend.domain.selfStudy.persistence.repository.jpa

import kr.flooding.backend.domain.selfStudy.persistence.entity.SelfStudyRoom
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface SelfStudyRoomJpaRepository : JpaRepository<SelfStudyRoom, Long> {
    fun findByIdIsNotNull(): Optional<SelfStudyRoom>
}
