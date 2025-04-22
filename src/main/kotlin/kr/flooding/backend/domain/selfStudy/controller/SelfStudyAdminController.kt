package kr.flooding.backend.domain.selfStudy.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import kr.flooding.backend.domain.selfStudy.dto.ChangeSelfStudyLimitRequest
import kr.flooding.backend.domain.selfStudy.usecase.CancelSelfStudyUsecase
import kr.flooding.backend.domain.selfStudy.usecase.ChangeSelfStudyLimitUsecase
import kr.flooding.backend.domain.selfStudy.usecase.ReserveSelfStudyUsecase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@Tag(name = "Self Study Management", description = "자습 관리")
@RestController
@RequestMapping("/admin/self-study")
class SelfStudyAdminController(
	private val changeSelfStudyLimitUsecase: ChangeSelfStudyLimitUsecase,
) {
	@Operation(summary = "자습 최대 인원 변경")
	@PatchMapping("/limit")
	fun changeSelfStudyLimit(
		@RequestBody request: ChangeSelfStudyLimitRequest
	): ResponseEntity<Unit> =
		changeSelfStudyLimitUsecase.execute(request).let {
			ResponseEntity.ok().build()
		}
}
