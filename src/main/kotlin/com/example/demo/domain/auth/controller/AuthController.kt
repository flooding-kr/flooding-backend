package com.example.demo.domain.auth.controller

import com.example.demo.domain.auth.dto.request.SignInRequest
import com.example.demo.domain.auth.dto.request.SignUpRequest
import com.example.demo.domain.auth.dto.response.SignInResponse
import com.example.demo.domain.auth.usecase.SignInUsecase
import com.example.demo.domain.auth.usecase.SignUpUsecase
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("auth")
class AuthController (
	private val signUpUsecase: SignUpUsecase,
	private val signInUsecase: SignInUsecase
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
	fun logout(){
		// TODO 로그아웃 구현
	}

	@DeleteMapping("withdraw")
	fun withdraw(){
		// TODO 회원탈퇴 구현
	}

	@PatchMapping("refresh")
	fun reissueRefreshToken(){
		// TODO 리프레시 토큰 재발급 구현
	}
}