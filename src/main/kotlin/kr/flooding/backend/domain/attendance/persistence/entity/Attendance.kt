package kr.flooding.backend.domain.attendance.persistence.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint
import kr.flooding.backend.domain.classroom.persistence.entity.Classroom
import kr.flooding.backend.domain.club.persistence.entity.Club
import kr.flooding.backend.domain.homebaseTable.persistence.entity.HomebaseTable
import kr.flooding.backend.domain.user.persistence.entity.User
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import org.hibernate.annotations.UuidGenerator
import java.time.LocalDate
import java.util.UUID

@Entity
@Table(
	name = "attendance",
	uniqueConstraints = [
		UniqueConstraint(columnNames = ["student_id", "period", "attended_at", "club_id"]),
		UniqueConstraint(columnNames = ["student_id", "period", "attended_at", "homebase_table_id"])
	]
)
data class Attendance(
	@Id
	@UuidGenerator(style = UuidGenerator.Style.RANDOM)
	val id: UUID? = null,

	@ManyToOne(fetch = FetchType.LAZY)
	@OnDelete(action = OnDeleteAction.SET_NULL)
	val classroom: Classroom? = null,

	@ManyToOne(fetch = FetchType.LAZY)
	@OnDelete(action = OnDeleteAction.SET_NULL)
	val homebaseTable: HomebaseTable? = null,

	@ManyToOne(fetch = FetchType.LAZY)
	@OnDelete(action = OnDeleteAction.CASCADE)
	val student: User,

	val period: Int,

	@CreationTimestamp
	@Column(nullable = false)
	val attendedAt: LocalDate? = null,

	@ManyToOne(fetch = FetchType.LAZY)
	@OnDelete(action = OnDeleteAction.SET_NULL)
	val club: Club? = null,

	val reason: String? = null,

	@Column(nullable = false)
	val isPresent: Boolean = true,
)
