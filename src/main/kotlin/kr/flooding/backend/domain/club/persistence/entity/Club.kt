package kr.flooding.backend.domain.club.persistence.entity

import jakarta.persistence.Column
import jakarta.persistence.ElementCollection
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint
import kr.flooding.backend.domain.classroom.persistence.entity.Classroom
import kr.flooding.backend.domain.club.enums.ClubStatus
import kr.flooding.backend.domain.club.enums.ClubType
import kr.flooding.backend.domain.user.persistence.entity.User
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import org.hibernate.annotations.UuidGenerator
import java.util.UUID

@Entity
@Table(
	name = "club",
	uniqueConstraints = [UniqueConstraint(columnNames = ["leader_id", "type"])],
)
data class Club(
	@Id
	@UuidGenerator
	val id: UUID? = null,

	@Column(nullable = false)
	val name: String,

	@Column(nullable = false)
	val description: String,

	@ManyToOne(fetch = FetchType.LAZY)
	@OnDelete(action = OnDeleteAction.SET_NULL)
	val classroom: Classroom?,

	val thumbnailImageKey: String? = null,

	@ElementCollection
	val activityImageKeys: List<String> = listOf(),

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	val type: ClubType,

	@ManyToOne(fetch = FetchType.LAZY)
	@OnDelete(action = OnDeleteAction.SET_NULL)
	val leader: User?,

	@ManyToOne(fetch = FetchType.LAZY)
	@OnDelete(action = OnDeleteAction.SET_NULL)
	val teacher: User? = null,

	@Column(nullable = false)
	var isRecruiting: Boolean = false,
) {
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	var status: ClubStatus = ClubStatus.PENDING
		protected set

	fun startRecruitment() {
		isRecruiting = true
	}

	fun stopRecruitment() {
		isRecruiting = false
	}

	fun approve() {
		status = ClubStatus.APPROVED
	}
}
