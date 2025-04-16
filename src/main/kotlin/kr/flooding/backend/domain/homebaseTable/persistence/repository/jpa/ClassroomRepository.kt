package kr.flooding.backend.domain.homebaseTable.persistence.repository.jpa

import kr.flooding.backend.domain.classroom.persistence.entity.Classroom
import org.springframework.data.jpa.repository.JpaRepository

interface ClassroomRepository : JpaRepository<Classroom, Long>
