package team.gsm.flooding.domain.homebase.entity

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UuidGenerator
import team.gsm.flooding.domain.attendance.entity.Attendance
import team.gsm.flooding.domain.classroom.entity.HomebaseTable
import java.time.LocalDate
import java.util.UUID

@Entity
data class HomebaseGroup(
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
	@Column(nullable = false)
	val attendedAt: LocalDate? = null,
)
