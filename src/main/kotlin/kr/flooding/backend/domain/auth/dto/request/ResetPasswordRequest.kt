package kr.flooding.backend.domain.auth.dto.request

import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern

data class ResetPasswordRequest(
	@field:NotNull
	val email: String,
	@field:NotNull
	val code: String,
	@Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[\\W_]).{8,}\$\n", message = "특수문자, 영문과 숫자를 포함한 8자리 이상의 비밀번호를 만들어주세요.")
	val password: String,
)
