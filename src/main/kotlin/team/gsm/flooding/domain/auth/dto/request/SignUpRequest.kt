package team.gsm.flooding.domain.auth.dto.request

import jakarta.validation.constraints.Pattern

data class SignUpRequest (
	@Pattern(regexp = "^[a-zA-Z0-9]{1,64}@gsm\\.hs\\.kr\$\n", message = "gsm.hs.kr 도메인의 이메일이어야 합니다.")
	val email: String,

	@Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[\\W_]).{8,}\$\n", message = "특수문자, 영문과 숫자를 포함한 8자리 이상의 비밀번호를 만들어주세요.")
	val password: String,

	@Pattern(regexp = "^[1-3][1-4][0-9]{2}\$\n", message = "학번의 형식이 올바르지 않습니다.")
	val schoolNumber: String
)