package kr.flooding.backend.domain.user.controller

import kr.flooding.backend.domain.user.dto.request.WithdrawRequest
import kr.flooding.backend.domain.user.dto.response.FetchUserInfoResponse
import kr.flooding.backend.domain.user.usecase.FetchFilteredUserUsecase
import kr.flooding.backend.domain.user.usecase.FetchUserUsecase
import kr.flooding.backend.domain.user.usecase.WithdrawUsecase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("user")
class UserController(
	private val fetchUserUsecase: FetchUserUsecase,
	private val withdrawUsecase: WithdrawUsecase,
	private val fetchFilteredUserUsecase: FetchFilteredUserUsecase,
) {
	@GetMapping
	fun getUserInfo(): ResponseEntity<FetchUserInfoResponse> =
		fetchUserUsecase.execute().let {
			ResponseEntity.ok(it)
		}

	@GetMapping("search")
	fun searchUser(
		@RequestParam("name", required = false) name: String?,
	): ResponseEntity<List<FetchUserInfoResponse>> =
		fetchFilteredUserUsecase.execute(name ?: "").let {
			ResponseEntity.ok(it)
		}

	@DeleteMapping("withdraw")
	fun withdraw(
		@RequestBody withdrawRequest: WithdrawRequest,
	): ResponseEntity<Unit> =
		withdrawUsecase.execute(withdrawRequest.password).let {
			ResponseEntity.ok().build()
		}
}
