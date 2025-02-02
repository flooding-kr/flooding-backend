package team.gsm.flooding.domain.user.entity

import team.gsm.flooding.global.converter.StringListConverter
import jakarta.persistence.*
import org.hibernate.annotations.ColumnDefault
import org.hibernate.annotations.Type
import org.hibernate.annotations.UuidGenerator
import java.util.UUID

@Entity(name = "users")
data class User (
	@Id
	@UuidGenerator
	@GeneratedValue
	val id: UUID? = null,

	@Column(unique = true)
	val email: String,

	val encodedPassword: String,

	val isVerified: Boolean,

	@Embedded
	val studentInfo: StudentInfo,

	val gender: Gender,

	val name: String,

	@Convert(converter = StringListConverter::class)
	val roles: List<Role>
)