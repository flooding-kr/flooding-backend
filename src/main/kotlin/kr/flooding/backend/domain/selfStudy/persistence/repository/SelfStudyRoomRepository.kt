package kr.flooding.backend.domain.selfStudy.persistence.repository

import kr.flooding.backend.domain.selfStudy.persistence.entity.SelfStudyRoom
import org.springframework.data.jpa.repository.JpaRepository

interface SelfStudyRoomRepository : JpaRepository<SelfStudyRoom, Long>
