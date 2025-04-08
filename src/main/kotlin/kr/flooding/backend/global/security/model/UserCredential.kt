package kr.flooding.backend.global.security.model

import java.util.UUID

class UserCredential(
	val id: UUID,
	val email: String,
	val encodedPassword: String,
)
