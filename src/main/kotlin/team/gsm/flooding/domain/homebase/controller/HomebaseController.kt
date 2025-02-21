package team.gsm.flooding.domain.homebase.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import team.gsm.flooding.domain.homebase.dto.request.FetchReservedHomebaseTableRequest
import team.gsm.flooding.domain.homebase.dto.request.ReserveHomebaseTableRequest
import team.gsm.flooding.domain.homebase.dto.response.FetchMyReservedHomebaseResponse
import team.gsm.flooding.domain.homebase.dto.response.FetchReservedHomebaseResponse
import team.gsm.flooding.domain.homebase.usecase.CancelHomebaseTableUsecase
import team.gsm.flooding.domain.homebase.usecase.FetchMyReservedHomebaseUsecase
import team.gsm.flooding.domain.homebase.usecase.FetchReservedHomebaseTableUsecase
import team.gsm.flooding.domain.homebase.usecase.ReserveHomebaseTableUsecase
import java.util.UUID

@RestController
@RequestMapping("/homebase")
class HomebaseController(
	private val reserveHomebaseTableUsecase: ReserveHomebaseTableUsecase,
	private val findMyReservedHomebaseTableUsecase: FetchMyReservedHomebaseUsecase,
	private val fetchReservedHomebaseTableUsecase: FetchReservedHomebaseTableUsecase,
	private val cancelHomebaseTableUsecase: CancelHomebaseTableUsecase,
) {
	@PostMapping
	fun reserveHomebaseTable(
		@RequestBody request: ReserveHomebaseTableRequest,
	): ResponseEntity<Unit> =
		reserveHomebaseTableUsecase.execute(request).run {
			ResponseEntity.ok().build()
		}

	@GetMapping
	fun findReservedHomebaseTable(
		@RequestBody request: FetchReservedHomebaseTableRequest,
	): ResponseEntity<List<FetchReservedHomebaseResponse>> =
		fetchReservedHomebaseTableUsecase.execute(request).run {
			ResponseEntity.ok(this)
		}

	@GetMapping("myself")
	fun findMyReservedHomebaseTable(): ResponseEntity<List<FetchMyReservedHomebaseResponse>> =
		findMyReservedHomebaseTableUsecase.execute().run {
			ResponseEntity.ok(this)
		}

	@DeleteMapping("{homebaseGroupId}")
	fun cancelReserveHomebaseTable(
		@PathVariable homebaseGroupId: UUID,
	): ResponseEntity<Unit> =
		cancelHomebaseTableUsecase.execute(homebaseGroupId).run {
			ResponseEntity.ok().build()
		}
}
