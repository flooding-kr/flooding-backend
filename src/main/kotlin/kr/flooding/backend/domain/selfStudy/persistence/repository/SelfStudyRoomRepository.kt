package kr.flooding.backend.domain.selfStudy.persistence.repository

import kr.flooding.backend.domain.selfStudy.persistence.entity.SelfStudyRoom
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface SelfStudyRoomRepository : JpaRepository<SelfStudyRoom, Long> {
	fun findByIdIsNotNull(): Optional<SelfStudyRoom>
}
