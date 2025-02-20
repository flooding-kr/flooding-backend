package team.gsm.flooding.domain.club.entity

import jakarta.persistence.Column
import jakarta.persistence.Convert
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import org.hibernate.annotations.UuidGenerator
import team.gsm.flooding.domain.classroom.entity.Classroom
import team.gsm.flooding.domain.user.entity.User
import team.gsm.flooding.global.converter.StringListConverter
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
)
