package com.example.demo.domain.user.controller

import com.example.demo.domain.user.dto.request.WithdrawRequest
import com.example.demo.domain.user.usecase.GetUserUsecase
import com.example.demo.domain.user.usecase.WithdrawUsecase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("user")
class UserController (
	private val getUserUsecase: GetUserUsecase,
	private val withdrawUsecase: WithdrawUsecase
) {
	// TODO 임시 확인용
	@GetMapping
	fun getUserInfo() = getUserUsecase.execute()

	@DeleteMapping("withdraw")
	fun withdraw(@RequestBody withdrawRequest: WithdrawRequest): ResponseEntity<Unit> {
		return withdrawUsecase.execute(withdrawRequest.password).let {
			ResponseEntity.ok().build()
		}
	}
}