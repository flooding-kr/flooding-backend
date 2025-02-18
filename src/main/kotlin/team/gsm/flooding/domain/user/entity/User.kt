package team.gsm.flooding.domain.user.entity

import jakarta.persistence.Column
import jakarta.persistence.Convert
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import org.hibernate.annotations.UuidGenerator
import team.gsm.flooding.global.converter.StringListConverter
import java.util.UUID

@Entity(name = "users")
data class User(
	@Id
	@UuidGenerator
	@GeneratedValue
	val id: UUID? = null,

	@Column(unique = true, nullable = false)
	val email: String,

	@Column(nullable = false)
	val encodedPassword: String,

	val isVerified: Boolean,

	@Embedded
	val studentInfo: StudentInfo,

	@Column(nullable = false)
	val gender: Gender,

	@Column(nullable = false)
	val name: String,

	@Convert(converter = StringListConverter::class)
	val roles: List<Role>,
)
