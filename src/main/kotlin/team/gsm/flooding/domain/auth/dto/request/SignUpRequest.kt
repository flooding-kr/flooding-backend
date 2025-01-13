package team.gsm.flooding.domain.auth.dto.request

import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Positive
import team.gsm.flooding.domain.user.entity.Gender

data class SignUpRequest (
	@Pattern(regexp = "^[a-zA-Z0-9]{1,64}@gsm\\.hs\\.kr\$\n", message = "gsm.hs.kr 도메인의 이메일이어야 합니다.")
	val email: String,

	@Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[\\W_]).{8,}\$\n", message = "특수문자, 영문과 숫자를 포함한 8자리 이상의 비밀번호를 만들어주세요.")
	val password: String,

	@field:Min(1)
	@field:Max(4)
	val classroom: Int,

	@field:Min(1)
	@field:Max(30)
	val number: Int,

	@field:Positive
	val year: Int,

	val name: String,

	val gender: Gender
)