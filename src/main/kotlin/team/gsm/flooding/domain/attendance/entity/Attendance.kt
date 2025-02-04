package team.gsm.flooding.domain.attendance.entity

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import org.hibernate.annotations.UuidGenerator
import team.gsm.flooding.domain.classroom.entity.Classroom
import team.gsm.flooding.domain.classroom.entity.HomebaseTable
import team.gsm.flooding.domain.user.entity.User
import java.time.LocalDate
import java.util.UUID

@Entity
data class Attendance (
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
	val attendedAt: LocalDate? = null
)