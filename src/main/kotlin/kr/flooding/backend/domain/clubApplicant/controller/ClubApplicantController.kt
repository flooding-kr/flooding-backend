package kr.flooding.backend.domain.clubApplicant.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import kr.flooding.backend.domain.clubApplicant.dto.request.ApplyClubRequest
import kr.flooding.backend.domain.clubApplicant.dto.request.ApproveClubApplicantRequest
import kr.flooding.backend.domain.clubApplicant.dto.request.RejectClubApplicantRequest
import kr.flooding.backend.domain.clubApplicant.dto.response.FetchClubApplicantResponse
import kr.flooding.backend.domain.clubApplicant.usecase.ApplyClubUsecase
import kr.flooding.backend.domain.clubApplicant.usecase.ApproveClubApplicantUsecase
import kr.flooding.backend.domain.clubApplicant.usecase.FetchClubApplicantUsecase
import kr.flooding.backend.domain.clubApplicant.usecase.RejectClubApplicantUsecase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@Tag(name = "Club Applicant", description = "동아리 지원")
@RestController
@RequestMapping("/club/applicant")
class ClubApplicantController(
	private val applyClubUsecase: ApplyClubUsecase,
	private val fetchClubApplicantUsecase: FetchClubApplicantUsecase,
	private val approveClubApplicantUsecase: ApproveClubApplicantUsecase,
	private val rejectClubApplicantUsecase: RejectClubApplicantUsecase,
) {
	@Operation(summary = "동아리 지원")
	@PostMapping
	fun applyClub(
		@Valid @RequestBody applyClubRequest: ApplyClubRequest,
	): ResponseEntity<Unit> =
		applyClubUsecase.execute(applyClubRequest).run {
			ResponseEntity.ok().build()
		}

	@Operation(summary = "동아리 지원 승인")
	@PostMapping("/approve")
	fun approveClubApplicant(
		@Valid @RequestBody approveClubApplicantRequest: ApproveClubApplicantRequest,
	): ResponseEntity<Unit> =
		approveClubApplicantUsecase.execute(approveClubApplicantRequest).run {
			ResponseEntity.ok().build()
		}

	@Operation(summary = "동아리 지원 반려")
	@DeleteMapping("/reject")
	fun rejectClubApplicant(
		@Valid @RequestBody rejectClubApplicantRequest: RejectClubApplicantRequest,
	): ResponseEntity<Unit> =
		rejectClubApplicantUsecase.execute(rejectClubApplicantRequest).run {
			ResponseEntity.ok().build()
		}

	@Operation(summary = "동아리 지원자 조회")
	@GetMapping
	fun fetchClubApplicant(
		@RequestParam clubId: UUID,
	): ResponseEntity<FetchClubApplicantResponse> =
		fetchClubApplicantUsecase.execute(clubId).let {
			ResponseEntity.ok(it)
		}
}
