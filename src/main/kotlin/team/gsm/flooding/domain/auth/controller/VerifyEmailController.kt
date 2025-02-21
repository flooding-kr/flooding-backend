package team.gsm.flooding.domain.auth.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import team.gsm.flooding.domain.auth.dto.request.RegenerateEmailCodeRequest
import team.gsm.flooding.domain.auth.usecase.RegenerateEmailCodeUsecase
import team.gsm.flooding.domain.auth.usecase.VerifyEmailUsecase

@RestController
@RequestMapping("signup/auth")
class VerifyEmailController(
	private val verifyEmailUsecase: VerifyEmailUsecase,
	private val regenerateEmailCodeUsecase: RegenerateEmailCodeUsecase,
) {
	@GetMapping("verify")
	fun verifyEmail(
		@RequestParam("email") email: String,
		@RequestParam("code") code: String,
	): ResponseEntity<String> =
		verifyEmailUsecase.execute(email, code).run {
			ResponseEntity.ok("인증에 성공하였습니다.")
		}

	@PatchMapping("re-verify")
	fun regenerateVerifyCode(
		@RequestBody regenerateEmailCodeRequest: RegenerateEmailCodeRequest,
	): ResponseEntity<Unit> =
		regenerateEmailCodeUsecase.execute(regenerateEmailCodeRequest.email).let {
			ResponseEntity.ok().build()
		}
}
