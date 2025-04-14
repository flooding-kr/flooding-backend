package kr.flooding.backend.domain.auth.dto.request

import jakarta.validation.constraints.Pattern
import kr.flooding.backend.domain.user.enums.Department
import kr.flooding.backend.domain.user.enums.Gender

data class SignUpTeacherRequest(
	@field:Pattern(regexp = "^[a-zA-Z0-9._%+-]{1,64}@gsm.hs.kr$", message = "gsm.hs.kr 도메인의 이메일이어야 합니다.")
	val email: String,
	@field:Pattern(
		regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[\\W_]).{8,}$",
		message = "특수문자, 영문과 숫자를 포함한 8자리 이상의 비밀번호를 만들어주세요.",
	)
	val password: String,
	val name: String,
	val gender: Gender,
	val department: Department,
)
