package team.gsm.flooding.domain.attendance.entity

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UuidGenerator
import team.gsm.flooding.domain.classroom.entity.HomebaseTable
import team.gsm.flooding.domain.user.entity.User
import java.time.LocalDate
import java.util.UUID

@Entity
data class AttendanceGroup (
	@Id
	@UuidGenerator(style = UuidGenerator.Style.RANDOM)
	val id: UUID? = null,

	@ManyToOne
	val homebaseTable: HomebaseTable,

	val period: Int,

	@OneToMany(cascade = [CascadeType.ALL], orphanRemoval = true)
	val participants: MutableList<Attendance> = mutableListOf(),

	@ManyToOne(cascade = [CascadeType.ALL])
	val proposer: Attendance,

	@CreationTimestamp
	val attendedAt: LocalDate? = null
)