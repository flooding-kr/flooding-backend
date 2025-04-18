package kr.flooding.backend.domain.user.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import kr.flooding.backend.domain.user.dto.web.response.FetchPendingUserListResponse
import kr.flooding.backend.domain.user.usecase.admin.ApproveSignUpUsecase
import kr.flooding.backend.domain.user.usecase.admin.FetchPendingUserListUsecase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@Tag(name = "User Management", description = "회원 관리")
@RestController
@RequestMapping("/admin/user")
class UserAdminController(
	private val approveSignUpUsecase: ApproveSignUpUsecase,
	private val fetchPendingUserListUsecase: FetchPendingUserListUsecase,
) {
	@Operation(summary = "회원 가입 승인")
	@PatchMapping("/{userId}/approve")
	fun approveSignUp(
		@PathVariable userId: UUID,
	): ResponseEntity<Unit> =
		approveSignUpUsecase.execute(userId).let {
			ResponseEntity.ok().build()
		}

	@Operation(summary = "승인 대기 목록 조회")
	@GetMapping("/pending")
	fun fetchPendingUsers(): ResponseEntity<FetchPendingUserListResponse> =
		fetchPendingUserListUsecase.execute().let {
			ResponseEntity.ok(it)
		}
}
