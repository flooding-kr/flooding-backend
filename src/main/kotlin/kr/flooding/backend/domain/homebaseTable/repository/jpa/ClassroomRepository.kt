package kr.flooding.backend.domain.homebaseTable.repository.jpa

import kr.flooding.backend.domain.classroom.entity.Classroom
import org.springframework.data.jpa.repository.JpaRepository

interface ClassroomRepository : JpaRepository<Classroom, Long>
