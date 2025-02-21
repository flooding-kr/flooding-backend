package team.gsm.flooding.domain.auth.controller

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
import team.gsm.flooding.domain.auth.dto.request.ReissueEmailCodeRequest
import team.gsm.flooding.domain.auth.dto.request.SignInRequest
import team.gsm.flooding.domain.auth.dto.request.SignUpRequest
import team.gsm.flooding.domain.auth.dto.response.ReissueTokenResponse
import team.gsm.flooding.domain.auth.dto.response.SignInResponse
import team.gsm.flooding.domain.auth.usecase.ReissueEmailCodeUsecase
import team.gsm.flooding.domain.auth.usecase.ReissueTokenUsecase
import team.gsm.flooding.domain.auth.usecase.SignInUsecase
import team.gsm.flooding.domain.auth.usecase.SignOutUsecase
import team.gsm.flooding.domain.auth.usecase.SignUpUsecase
import team.gsm.flooding.domain.auth.usecase.VerifyEmailUsecase

@RestController
@RequestMapping("auth")
class AuthController(
	private val signUpUsecase: SignUpUsecase,
	private val signInUsecase: SignInUsecase,
	private val reissueTokenUsecase: ReissueTokenUsecase,
	private val signOutUsecase: SignOutUsecase,
	private val verifyEmailUsecase: VerifyEmailUsecase,
	private val reissueEmailCodeUsecase: ReissueEmailCodeUsecase,
) {
	@PostMapping("sign-up")
	fun signUp(
		@Valid @RequestBody signUpRequest: SignUpRequest,
	): ResponseEntity<Unit> =
		signUpUsecase.execute(signUpRequest).let {
			ResponseEntity.ok().build()
		}

	@PostMapping("sign-in")
	fun signIn(
		@Valid @RequestBody signInRequest: SignInRequest,
	): ResponseEntity<SignInResponse> =
		signInUsecase.execute(signInRequest).let {
			ResponseEntity.ok(it)
		}

	@PostMapping("logout")
	fun signOut(
		@RequestHeader("Refresh-Token") refreshToken: String,
	): ResponseEntity<Unit> {
		val resolveRefreshToken = refreshToken.substring(7)
		return signOutUsecase.execute(resolveRefreshToken).let {
			ResponseEntity.ok().build()
		}
	}

	@PatchMapping("re-issue")
	fun reissueToken(
		@RequestHeader("Refresh-Token") refreshToken: String,
	): ResponseEntity<ReissueTokenResponse> {
		val resolveRefreshToken = refreshToken.substring(7)
		return reissueTokenUsecase.execute(resolveRefreshToken).let {
			ResponseEntity.ok(it)
		}
	}

	@GetMapping("verify")
	fun verifyEmail(
		@RequestParam("email") email: String,
		@RequestParam("code") code: String,
	): ResponseEntity<Unit> =
		verifyEmailUsecase.execute(email, code).let {
			ResponseEntity.ok().build()
		}

	@PatchMapping("re-verify")
	fun reissueVerifyCode(
		@RequestBody reissueEmailCodeRequest: ReissueEmailCodeRequest,
	): ResponseEntity<Unit> =
		reissueEmailCodeUsecase.execute(reissueEmailCodeRequest.email).let {
			ResponseEntity.ok().build()
		}
}
