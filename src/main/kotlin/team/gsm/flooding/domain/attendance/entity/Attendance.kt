package team.gsm.flooding.domain.attendance.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UuidGenerator
import team.gsm.flooding.domain.classroom.entity.Classroom
import team.gsm.flooding.domain.classroom.entity.HomebaseTable
import team.gsm.flooding.domain.user.entity.User
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
