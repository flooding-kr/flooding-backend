package kr.flooding.backend.domain.classroom.repository

import kr.flooding.backend.domain.classroom.entity.Classroom
import org.springframework.data.jpa.repository.JpaRepository

interface ClassroomRepository : JpaRepository<Classroom, Long>
