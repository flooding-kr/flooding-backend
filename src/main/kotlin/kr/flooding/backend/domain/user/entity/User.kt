package kr.flooding.backend.domain.user.entity

import jakarta.persistence.Column
import jakarta.persistence.Convert
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import kr.flooding.backend.global.converter.StringListConverter
import org.hibernate.annotations.UuidGenerator
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
	@Enumerated(EnumType.STRING)
	val gender: Gender,

	@Column(nullable = false)
	val name: String,

	@Convert(converter = StringListConverter::class)
	val roles: List<Role>,
)
