package kr.flooding.backend.domain.massage.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import kr.flooding.backend.domain.massage.usecase.ReserveMassageUsecase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Massage", description = "안마 의자")
@RestController
@RequestMapping("massage")
class MassageController(
	private val reserveMassageUsecase: ReserveMassageUsecase,
) {
	@Operation(summary = "안마의자 신청")
	@PostMapping
	fun reserveMassage(): ResponseEntity<Unit> =
		reserveMassageUsecase.execute().run {
			ResponseEntity.ok().build()
		}
}
