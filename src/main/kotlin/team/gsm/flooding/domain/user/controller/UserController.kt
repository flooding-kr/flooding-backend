package team.gsm.flooding.domain.user.controller

import team.gsm.flooding.domain.user.dto.request.WithdrawRequest
import team.gsm.flooding.domain.user.usecase.FetchUserUsecase
import team.gsm.flooding.domain.user.usecase.WithdrawUsecase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("user")
class UserController (
	private val fetchUserUsecase: FetchUserUsecase,
	private val withdrawUsecase: WithdrawUsecase
) {
	// TODO 임시 확인용
	@GetMapping
	fun getUserInfo() = fetchUserUsecase.execute()

	@DeleteMapping("withdraw")
	fun withdraw(@RequestBody withdrawRequest: WithdrawRequest): ResponseEntity<Unit> {
		return withdrawUsecase.execute(withdrawRequest.password).let {
			ResponseEntity.ok().build()
		}
	}
}