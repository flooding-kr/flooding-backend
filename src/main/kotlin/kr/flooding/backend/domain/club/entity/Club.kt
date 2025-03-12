package kr.flooding.backend.domain.club.entity

import jakarta.persistence.Column
import jakarta.persistence.Convert
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import kr.flooding.backend.domain.classroom.entity.Classroom
import kr.flooding.backend.domain.user.entity.User
import kr.flooding.backend.global.database.converter.StringListConverter
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import org.hibernate.annotations.UuidGenerator
import java.util.UUID

@Entity
data class Club(
	@Id
	@UuidGenerator
	val id: UUID? = null,

	@Column(nullable = false)
	val name: String,

	@Column(nullable = false)
	val description: String,

	@ManyToOne
	@OnDelete(action = OnDeleteAction.SET_NULL)
	val classroom: Classroom,

	val thumbnailImageUrl: String? = null,

	@Convert(converter = StringListConverter::class)
	val activityImageUrls: List<String> = listOf(),

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	val status: ClubStatus,

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	val type: ClubType,

	@ManyToOne
	@OnDelete(action = OnDeleteAction.SET_NULL)
	val leader: User,

	@Column(nullable = false)
	var isRecruiting: Boolean = false,
) {
	fun startRecruitment() {
		isRecruiting = true
	}

	fun stopRecruitment() {
		isRecruiting = false
	}
}
