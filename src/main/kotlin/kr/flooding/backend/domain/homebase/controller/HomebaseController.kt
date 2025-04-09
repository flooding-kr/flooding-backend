package kr.flooding.backend.domain.homebase.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import kr.flooding.backend.domain.homebase.dto.request.FetchReservedHomebaseTableRequest
import kr.flooding.backend.domain.homebase.dto.request.ReserveHomebaseTableRequest
import kr.flooding.backend.domain.homebase.dto.response.FetchMyReservedHomebaseResponse
import kr.flooding.backend.domain.homebase.dto.response.FetchReservedHomebaseResponse
import kr.flooding.backend.domain.homebase.usecase.CancelHomebaseTableUsecase
import kr.flooding.backend.domain.homebase.usecase.FetchMyReservedHomebaseUsecase
import kr.flooding.backend.domain.homebase.usecase.FetchReservedHomebaseTableUsecase
import kr.flooding.backend.domain.homebase.usecase.ReserveHomebaseTableUsecase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@Tag(name = "Homebase", description = "홈베이스")
@RestController
@RequestMapping("/homebase")
class HomebaseController(
	private val reserveHomebaseTableUsecase: ReserveHomebaseTableUsecase,
	private val findMyReservedHomebaseTableUsecase: FetchMyReservedHomebaseUsecase,
	private val fetchReservedHomebaseTableUsecase: FetchReservedHomebaseTableUsecase,
	private val cancelHomebaseTableUsecase: CancelHomebaseTableUsecase,
) {
	@Operation(summary = "홈베이스 예약")
	@PostMapping
	fun reserveHomebaseTable(
		@RequestBody request: ReserveHomebaseTableRequest,
	): ResponseEntity<Unit> =
		reserveHomebaseTableUsecase.execute(request).run {
			ResponseEntity.ok().build()
		}

	@Operation(summary = "홈베이스 자리 조회")
	@GetMapping
	fun findReservedHomebaseTable(
		@RequestParam floor: Int,
		@RequestParam period: Int,
	): ResponseEntity<List<FetchReservedHomebaseResponse>> =
		fetchReservedHomebaseTableUsecase
			.execute(
				FetchReservedHomebaseTableRequest(
					floor = floor,
					period = period,
				),
			).run {
				ResponseEntity.ok(this)
			}

	@Operation(summary = "예약한 홈베이스 조회")
	@GetMapping("myself")
	fun findMyReservedHomebaseTable(): ResponseEntity<List<FetchMyReservedHomebaseResponse>> =
		findMyReservedHomebaseTableUsecase.execute().run {
			ResponseEntity.ok(this)
		}

	@Operation(summary = "홈베이스 예약 취소")
	@DeleteMapping("{homebaseGroupId}")
	fun cancelReserveHomebaseTable(
		@PathVariable homebaseGroupId: UUID,
	): ResponseEntity<Unit> =
		cancelHomebaseTableUsecase.execute(homebaseGroupId).run {
			ResponseEntity.ok().build()
		}
}
