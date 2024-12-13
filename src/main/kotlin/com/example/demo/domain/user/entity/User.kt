package com.example.demo.domain.user.entity

import com.example.demo.global.util.StringListConverter
import jakarta.persistence.Convert
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.Id
import org.hibernate.annotations.UuidGenerator
import java.util.UUID

@Entity(name = "users")
class User (
	@Id
	@UuidGenerator
	val id: UUID?,

	val email: String,

	val encodedPassword: String,

	@Embedded
	val studentInfo: StudentInfo,

	@Convert(converter = StringListConverter::class)
	val roles: List<Role>
)