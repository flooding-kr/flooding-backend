package kr.flooding.backend.domain.user.controller

import io.swagger.v3.oas.annotations.tags.Tag
import kr.flooding.backend.domain.user.dto.request.WithdrawRequest
import kr.flooding.backend.domain.user.dto.response.FetchUserInfoResponse
import kr.flooding.backend.domain.user.usecase.FetchFilteredUserUsecase
import kr.flooding.backend.domain.user.usecase.FetchUserUsecase
import kr.flooding.backend.domain.user.usecase.UpdateProfileImageUsecase
import kr.flooding.backend.domain.user.usecase.WithdrawUsecase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@Tag(name = "User", description = "회원")
@RestController
@RequestMapping("user")
class UserController(
	private val fetchUserUsecase: FetchUserUsecase,
	private val withdrawUsecase: WithdrawUsecase,
	private val fetchFilteredUserUsecase: FetchFilteredUserUsecase,
	private val updateProfileImageUsecase: UpdateProfileImageUsecase,
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

	@PatchMapping("profile")
	fun updateProfileImage(
		@RequestPart("image") image: MultipartFile,
	): ResponseEntity<Unit> =
		updateProfileImageUsecase.execute(image).let {
			ResponseEntity.ok().build()
		}
}
