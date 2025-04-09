package kr.flooding.backend.domain.clubApplicant.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import kr.flooding.backend.domain.clubApplicant.dto.request.ApplyClubRequest
import kr.flooding.backend.domain.clubApplicant.dto.request.ApproveClubApplicantRequest
import kr.flooding.backend.domain.clubApplicant.dto.response.FetchClubApplicantResponse
import kr.flooding.backend.domain.clubApplicant.usecase.ApplyClubUsecase
import kr.flooding.backend.domain.clubApplicant.usecase.ApproveClubApplicantUsecase
import kr.flooding.backend.domain.clubApplicant.usecase.FetchClubApplicantUsecase
import org.springframework.http.ResponseEntity
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
) {
	@Operation(summary = "동아리 지원")
	@PostMapping
	fun applyClub(
		@Valid @RequestBody applyClubRequest: ApplyClubRequest,
	): ResponseEntity<Unit> =
		applyClubUsecase.execute(applyClubRequest).run {
			ResponseEntity.ok().build()
		}

	@Operation(summary = "동아리 지원 요청 승인")
	@PostMapping("/approve")
	fun approveClubApplicant(
		@Valid @RequestBody approveClubApplicantRequest: ApproveClubApplicantRequest,
	): ResponseEntity<Unit> =
		approveClubApplicantUsecase.execute(approveClubApplicantRequest).run {
			ResponseEntity.ok().build()
		}

	@Operation(summary = "동아리 구성원 조회")
	@GetMapping
	fun fetchClubApplicant(
		@RequestParam clubId: UUID,
	): ResponseEntity<FetchClubApplicantResponse> =
		fetchClubApplicantUsecase.execute(clubId).let {
			ResponseEntity.ok(it)
		}
}
