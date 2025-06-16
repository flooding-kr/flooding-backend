package kr.flooding.backend.domain.selfStudy.persistence.repository.jpa

import kr.flooding.backend.domain.selfStudy.persistence.entity.SelfStudyBan
import kr.flooding.backend.domain.user.persistence.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDate
import java.util.Optional
import java.util.UUID

interface SelfStudyBanJpaRepository: JpaRepository<SelfStudyBan, UUID> {
    fun existsByStudent(student: User): Boolean
    fun deleteAllByResumeDateBefore(currentDate: LocalDate)
    fun findByStudent(student: User): Optional<SelfStudyBan>
}