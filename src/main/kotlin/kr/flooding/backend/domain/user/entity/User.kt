package kr.flooding.backend.domain.user.entity

import jakarta.persistence.Column
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import kr.flooding.backend.domain.user.enums.Gender
import kr.flooding.backend.domain.user.enums.UserState
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
	var encodedPassword: String,

	var emailVerifyStatus: Boolean = false,

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	val userState: UserState = UserState.PENDING,

	@Embedded
	val studentInfo: StudentInfo? = null,

	@Embedded
	val teacherInfo: TeacherInfo? = null,

	var profileImageUrl: String? = null,

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	val gender: Gender,

	@Column(nullable = false)
	val name: String,

) {
	fun updateProfileImage(url: String) {
		profileImageUrl = url
	}

	fun resetPassword(password: String) {
		encodedPassword = password
	}

	fun enableEmailVerify() {
		emailVerifyStatus = true
	}
}
