package team.gsm.flooding.domain.lunch.controller

import team.gsm.flooding.domain.lunch.controller.dto.request.LunchTime
import team.gsm.flooding.domain.lunch.controller.dto.response.FetchLunchResponse
import team.gsm.flooding.domain.lunch.usecase.FetchLunchUsecase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate

@RestController
@RequestMapping("lunch")
class LunchController (
	private val fetchLunchUsecase: FetchLunchUsecase
) {
	@GetMapping
	fun fetchLunch(
		@RequestParam date: LocalDate,
		@RequestParam time: LunchTime
	): ResponseEntity<FetchLunchResponse> =
		fetchLunchUsecase.execute(date, time).let { ResponseEntity.ok(it) }
}