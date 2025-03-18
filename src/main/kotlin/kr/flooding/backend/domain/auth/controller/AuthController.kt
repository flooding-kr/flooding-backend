package kr.flooding.backend.domain.auth.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
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

@Tag(name = "Auth", description = "회원 인증")
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
	@Operation(summary = "회원가입")
	@ApiResponses(
		value = [
			ApiResponse(responseCode = "200", description = "성공"),
			ApiResponse(responseCode = "400", description = "잘못된 요청"),
			ApiResponse(responseCode = "500", description = "이메일 전송 실패 혹은 예기치 못한 오류"),
		],
	)
	@PostMapping("sign-up")
	fun signUp(
		@Valid @RequestBody signUpRequest: SignUpRequest,
	): ResponseEntity<Unit> =
		signUpUsecase.execute(signUpRequest).let {
			ResponseEntity.ok().build()
		}

	@Operation(summary = "로그인")
	@PostMapping("sign-in")
	fun signIn(
		@Valid @RequestBody signInRequest: SignInRequest,
	): ResponseEntity<SignInResponse> =
		signInUsecase.execute(signInRequest).let {
			ResponseEntity.ok(it)
		}

	@Operation(summary = "로그아웃")
	@PostMapping("logout")
	fun signOut(
		@RequestHeader("Refresh-Token") refreshToken: String,
	): ResponseEntity<Unit> {
		val resolveRefreshToken = refreshToken.substring(7)
		return signOutUsecase.execute(resolveRefreshToken).let {
			ResponseEntity.ok().build()
		}
	}

	@Operation(summary = "엑세스 토큰 재발급")
	@PatchMapping("re-issue")
	fun reissueToken(
		@RequestHeader("Refresh-Token") refreshToken: String,
	): ResponseEntity<ReissueTokenResponse> {
		val resolveRefreshToken = refreshToken.substring(7)
		return reissueTokenUsecase.execute(resolveRefreshToken).let {
			ResponseEntity.ok(it)
		}
	}

	@Operation(summary = "회원 이메일 인증")
	@GetMapping("verify")
	fun verifyEmail(
		@RequestParam("email") email: String,
		@RequestParam("code") code: String,
	): ResponseEntity<Unit> =
		verifyEmailUsecase.execute(email, code).let {
			ResponseEntity.ok().build()
		}

	@Operation(summary = "회원 인증 메일 재발송")
	@PatchMapping("re-verify")
	fun reissueVerifyCode(
		@RequestBody reissueEmailCodeRequest: ReissueEmailCodeRequest,
	): ResponseEntity<Unit> =
		reissueEmailCodeUsecase.execute(reissueEmailCodeRequest.email).let {
			ResponseEntity.ok().build()
		}

	@Operation(summary = "비밀번호 찾기 메일 발송")
	@PostMapping("/password/find")
	fun requestFindPassword(
		@RequestParam email: String,
	): ResponseEntity<Unit> =
		requestResetPasswordUsecase.execute(email).let {
			ResponseEntity.ok().build()
		}

	@Operation(summary = "이메일 인증으로 비밀번호 변경하기")
	@PostMapping("/password/reset")
	fun resetPassword(
		@RequestBody request: ResetPasswordRequest,
	): ResponseEntity<Unit> =
		resetPasswordUsecase.execute(request).let {
			ResponseEntity.ok().build()
		}
}
