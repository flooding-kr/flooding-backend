package kr.flooding.backend.domain.attendance.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import kr.flooding.backend.domain.classroom.entity.Classroom
import kr.flooding.backend.domain.classroom.entity.HomebaseTable
import kr.flooding.backend.domain.user.entity.User
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UuidGenerator
import java.time.LocalDate
import java.util.UUID

@Entity
data class Attendance(
	@Id
	@UuidGenerator(style = UuidGenerator.Style.RANDOM)
	val id: UUID? = null,

	@ManyToOne
	val classroom: Classroom? = null,

	@ManyToOne
	val homebaseTable: HomebaseTable? = null,

	@ManyToOne
	val student: User,

	val period: Int,

	@CreationTimestamp
	@Column(nullable = false)
	val attendedAt: LocalDate? = null,
)
