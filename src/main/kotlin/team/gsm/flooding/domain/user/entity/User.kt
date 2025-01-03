package team.gsm.flooding.domain.user.entity

import team.gsm.flooding.global.converter.StringListConverter
import jakarta.persistence.*
import org.hibernate.annotations.UuidGenerator
import java.util.UUID

@Entity(name = "users")
data class User (
	@Id
	@UuidGenerator(style = UuidGenerator.Style.TIME)
	val id: UUID? = null,

	val email: String,

	val encodedPassword: String,

	val isVerified: Boolean,

	@Embedded
	val studentInfo: StudentInfo,

	@Convert(converter = StringListConverter::class)
	val roles: List<Role>
)