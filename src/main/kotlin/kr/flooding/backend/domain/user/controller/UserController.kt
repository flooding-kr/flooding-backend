package kr.flooding.backend.domain.user.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import kr.flooding.backend.domain.user.dto.request.UpdateProfileRequest
import kr.flooding.backend.domain.user.dto.request.WithdrawRequest
import kr.flooding.backend.domain.user.dto.response.FetchUserInfoResponse
import kr.flooding.backend.domain.user.dto.response.SearchStudentListResponse
import kr.flooding.backend.domain.user.dto.response.SearchTeacherListResponse
import kr.flooding.backend.domain.user.usecase.FetchUserUsecase
import kr.flooding.backend.domain.user.usecase.SearchStudentUsecase
import kr.flooding.backend.domain.user.usecase.SearchTeacherUsecase
import kr.flooding.backend.domain.user.usecase.UpdateProfileImageUsecase
import kr.flooding.backend.domain.user.usecase.WithdrawUsecase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Tag(name = "User", description = "회원")
@RestController
@RequestMapping("user")
class UserController(
	private val fetchUserUsecase: FetchUserUsecase,
	private val withdrawUsecase: WithdrawUsecase,
	private val updateProfileImageUsecase: UpdateProfileImageUsecase,
	private val searchStudentUsecase: SearchStudentUsecase,
	private val searchTeacherUsecase: SearchTeacherUsecase,
) {
	@Operation(summary = "나의 정보 조회")
	@GetMapping
	fun getUserInfo(): ResponseEntity<FetchUserInfoResponse> =
		fetchUserUsecase.execute().let {
			ResponseEntity.ok(it)
		}

	@Operation(summary = "회원 탈퇴")
	@DeleteMapping("withdraw")
	fun withdraw(
		@RequestBody withdrawRequest: WithdrawRequest,
	): ResponseEntity<Unit> =
		withdrawUsecase.execute(withdrawRequest.password).let {
			ResponseEntity.ok().build()
		}

	@Operation(summary = "나의 정보 수정")
	@PatchMapping("profile")
	fun updateProfileImage(
		@RequestBody @Valid updateProfileRequest: UpdateProfileRequest,
	): ResponseEntity<Unit> =
		updateProfileImageUsecase.execute(updateProfileRequest).let {
			ResponseEntity.ok().build()
		}

	@Operation(summary = "학생 검색")
	@GetMapping("student/search")
	fun searchStudent(
		@RequestParam("name", required = false, defaultValue = "") name: String,
	): ResponseEntity<SearchStudentListResponse> =
		searchStudentUsecase.execute(name).let {
			ResponseEntity.ok(it)
		}

	@Operation(summary = "선생님 검색")
	@GetMapping("teacher/search")
	fun searchTeacher(
		@RequestParam("name", required = false, defaultValue = "") name: String,
	): ResponseEntity<SearchTeacherListResponse> =
		searchTeacherUsecase.execute(name).let {
			ResponseEntity.ok(it)
		}
}
