package team.gsm.flooding.domain.user.controller

import team.gsm.flooding.domain.user.dto.request.WithdrawRequest
import team.gsm.flooding.domain.user.usecase.FetchUserUsecase
import team.gsm.flooding.domain.user.usecase.WithdrawUsecase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import team.gsm.flooding.domain.user.dto.response.FetchUserInfoResponse
import team.gsm.flooding.domain.user.usecase.SearchUserUsecase

@RestController
@RequestMapping("user")
class UserController (
	private val fetchUserUsecase: FetchUserUsecase,
	private val withdrawUsecase: WithdrawUsecase,
	private val searchUserUsecase: SearchUserUsecase,
) {
	@GetMapping
	fun getUserInfo(): ResponseEntity<FetchUserInfoResponse> {
		return fetchUserUsecase.execute().let {
			ResponseEntity.ok(it)
		}
	}

	@GetMapping("search")
	fun searchUser(
		@RequestParam("name", required = true) name: String,
	): ResponseEntity<List<FetchUserInfoResponse>> {
		return searchUserUsecase.execute(name).let {
			ResponseEntity.ok(it)
		}
	}

	@DeleteMapping("withdraw")
	fun withdraw(@RequestBody withdrawRequest: WithdrawRequest): ResponseEntity<Unit> {
		return withdrawUsecase.execute(withdrawRequest.password).let {
			ResponseEntity.ok().build()
		}
	}
}