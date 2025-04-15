package kr.flooding.backend.domain.selfStudy.repository

import kr.flooding.backend.domain.selfStudy.entity.SelfStudyRoom
import org.springframework.data.jpa.repository.JpaRepository

interface SelfStudyRoomRepository : JpaRepository<SelfStudyRoom, Long>
