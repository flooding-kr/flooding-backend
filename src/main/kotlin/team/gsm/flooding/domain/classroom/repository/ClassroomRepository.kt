package team.gsm.flooding.domain.classroom.repository

import org.springframework.data.jpa.repository.JpaRepository
import team.gsm.flooding.domain.classroom.entity.Classroom

interface ClassroomRepository : JpaRepository<Classroom, String>
