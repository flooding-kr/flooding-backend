package team.gsm.flooding.domain.auth.controller

import team.gsm.flooding.domain.auth.dto.response.ReissueTokenResponse
import team.gsm.flooding.domain.auth.dto.response.SignInResponse
import team.gsm.flooding.domain.auth.usecase.*
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import team.gsm.flooding.domain.auth.dto.request.RegenerateEmailCodeRequest
import team.gsm.flooding.domain.auth.dto.request.SignInRequest
import team.gsm.flooding.domain.auth.dto.request.SignUpRequest

@RestController
@RequestMapping("auth")
class AuthController (
	private val signUpUsecase: SignUpUsecase,
	private val signInUsecase: SignInUsecase,
	private val reissueTokenUsecase: ReissueTokenUsecase,
	private val verifyEmailUsecase: VerifyEmailUsecase,
	private val regenerateEmailCodeUsecase: RegenerateEmailCodeUsecase,
	private val logoutUsecase: LogoutUsecase,
) {
	@PostMapping("sign-up")
	fun signUp(@Valid @RequestBody signUpRequest: SignUpRequest): ResponseEntity<Unit> {
		return signUpUsecase.execute(signUpRequest).let {
			ResponseEntity.ok().build()
		}
	}

	@PostMapping("sign-in")
	fun signIn(@Valid @RequestBody signInRequest: SignInRequest): ResponseEntity<SignInResponse> {
		return signInUsecase.execute(signInRequest).let {
			ResponseEntity.ok(it)
		}
	}

	@PostMapping("logout")
	fun logout(@RequestHeader("Refresh-Token") refreshToken: String): ResponseEntity<Unit> {
		val resolveRefreshToken = refreshToken.substring(7)
		return logoutUsecase.execute(resolveRefreshToken).let {
			ResponseEntity.ok().build()
		}
	}

	@PatchMapping("re-issue")
	fun reissueToken(@RequestHeader("Refresh-Token") refreshToken: String): ResponseEntity<ReissueTokenResponse> {
		val resolveRefreshToken = refreshToken.substring(7)
		return reissueTokenUsecase.execute(resolveRefreshToken).let {
			ResponseEntity.ok(it)
		}
	}

	@GetMapping("verify")
	fun verifyEmail(
		@RequestParam("email") email: String,
		@RequestParam("code") code: String
	): ResponseEntity<String> {
		verifyEmailUsecase.execute(email, code)
		return ResponseEntity.ok("인증에 성공하였습니다.")
	}

	@PatchMapping("re-verify")
	fun regenerateVerifyCode(
		@RequestBody regenerateEmailCodeRequest: RegenerateEmailCodeRequest
	): ResponseEntity<Unit> {
		return regenerateEmailCodeUsecase.execute(regenerateEmailCodeRequest.email).let {
			ResponseEntity.ok().build()
		}
	}
}