package kr.flooding.backend.domain.auth.controller

import jakarta.validation.Valid
import kr.flooding.backend.domain.auth.dto.request.ReissueEmailCodeRequest
import kr.flooding.backend.domain.auth.dto.request.ResetPasswordRequest
import kr.flooding.backend.domain.auth.dto.request.SignInRequest
import kr.flooding.backend.domain.auth.dto.request.SignUpRequest
import kr.flooding.backend.domain.auth.dto.response.ReissueTokenResponse
import kr.flooding.backend.domain.auth.dto.response.SignInResponse
import kr.flooding.backend.domain.auth.usecase.ReissueEmailCodeUsecase
import kr.flooding.backend.domain.auth.usecase.ReissueTokenUsecase
import kr.flooding.backend.domain.auth.usecase.RequestResetPasswordUsecase
import kr.flooding.backend.domain.auth.usecase.ResetPasswordUsecase
import kr.flooding.backend.domain.auth.usecase.SignInUsecase
import kr.flooding.backend.domain.auth.usecase.SignOutUsecase
import kr.flooding.backend.domain.auth.usecase.SignUpUsecase
import kr.flooding.backend.domain.auth.usecase.VerifyEmailUsecase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("auth")
class AuthController(
	private val signUpUsecase: SignUpUsecase,
	private val signInUsecase: SignInUsecase,
	private val reissueTokenUsecase: ReissueTokenUsecase,
	private val signOutUsecase: SignOutUsecase,
	private val verifyEmailUsecase: VerifyEmailUsecase,
	private val reissueEmailCodeUsecase: ReissueEmailCodeUsecase,
	private val requestResetPasswordUsecase: RequestResetPasswordUsecase,
	private val resetPasswordUsecase: ResetPasswordUsecase,
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

	@PostMapping("/password/find")
	fun requestFindPassword(
		@RequestParam email: String,
	): ResponseEntity<Unit> =
		requestResetPasswordUsecase.execute(email).let {
			ResponseEntity.ok().build()
		}

	@PostMapping("/password/reset")
	fun resetPassword(
		@RequestBody request: ResetPasswordRequest,
	): ResponseEntity<Unit> =
		resetPasswordUsecase.execute(request).let {
			ResponseEntity.ok().build()
		}
}
