package kr.flooding.backend.domain.attendance.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import kr.flooding.backend.domain.classroom.entity.Classroom
import kr.flooding.backend.domain.classroom.entity.HomebaseTable
import kr.flooding.backend.domain.club.entity.Club
import kr.flooding.backend.domain.homebaseTable.entity.HomebaseTable
import kr.flooding.backend.domain.user.entity.User
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import org.hibernate.annotations.UuidGenerator
import java.time.LocalDate
import java.util.UUID

@Entity
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

	@ManyToOne
	val club: Club? = null,

	val reason: String? = null,

	@Column(nullable = false)
	val isPresent: Boolean = true,

)
