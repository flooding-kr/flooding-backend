package team.gsm.flooding.domain.lunch.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import team.gsm.flooding.domain.lunch.controller.dto.request.LunchTime
import team.gsm.flooding.domain.lunch.controller.dto.response.FetchLunchResponse
import team.gsm.flooding.domain.lunch.usecase.FetchLunchUsecase
import java.time.LocalDate

@RestController
@RequestMapping("lunch")
class LunchController(
	private val fetchLunchUsecase: FetchLunchUsecase,
) {
	@GetMapping
	@Operation(summary = "급식 조회", description = "조회할 날짜와 시간대를 받아서 급식표를 반환합니다.")
	@ApiResponses(
		value = [
			ApiResponse(
				responseCode = "200",
				description = "성공적으로 급식표를 조회하였습니다.",
				content = [
					Content(
						mediaType = "application/json",
						schema = Schema(implementation = FetchLunchResponse::class),
					),
				],

			),
		],
	)
	fun fetchLunch(
		@Parameter(description = "조회할 날짜", example = "2007-03-27", required = true) @RequestParam date: LocalDate,
		@Parameter(description = "급식 시간대", example = "LUNCH", required = true) @RequestParam time: LunchTime,
	): ResponseEntity<FetchLunchResponse> = fetchLunchUsecase.execute(date, time).let { ResponseEntity.ok(it) }
}
