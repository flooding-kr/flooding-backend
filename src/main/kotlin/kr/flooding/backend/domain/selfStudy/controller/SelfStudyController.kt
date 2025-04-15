package kr.flooding.backend.domain.selfStudy.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import kr.flooding.backend.domain.selfStudy.usecase.CancelSelfStudyUsecase
import kr.flooding.backend.domain.selfStudy.usecase.ReserveSelfStudyUsecase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Self Study", description = "자습")
@RestController
@RequestMapping("/self-study")
class SelfStudyController(
	private val reserveSelfStudyUsecase: ReserveSelfStudyUsecase,
	private val cancelSelfStudyUsecase: CancelSelfStudyUsecase,
) {
	@Operation(summary = "자습 신청")
	@PostMapping
	fun reserveSelfStudy(): ResponseEntity<Unit> =
		reserveSelfStudyUsecase.execute().let {
			ResponseEntity.ok().build()
		}

	@Operation(summary = "자습 취소")
	@DeleteMapping
	fun cancelSelfStudy(): ResponseEntity<Unit> =
		cancelSelfStudyUsecase.execute().let {
			ResponseEntity.ok().build()
		}
}
