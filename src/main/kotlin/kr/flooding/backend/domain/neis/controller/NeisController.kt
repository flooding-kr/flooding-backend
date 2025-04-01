package kr.flooding.backend.domain.neis.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import kr.flooding.backend.domain.neis.dto.request.FetchTimetableRequest
import kr.flooding.backend.domain.neis.dto.request.LunchTime
import kr.flooding.backend.domain.neis.dto.response.FetchMealInfoResponse
import kr.flooding.backend.domain.neis.dto.response.FetchTimetableResponse
import kr.flooding.backend.domain.neis.usecase.FetchMealInfoUsecase
import kr.flooding.backend.domain.neis.usecase.FetchTimetableUsecase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate

@Tag(name = "Neis", description = "나이스 API")
@RestController
@RequestMapping("neis")
class NeisController(
	private val fetchMealInfoUsecase: FetchMealInfoUsecase,
	private val fetchTimetableUsecase: FetchTimetableUsecase,
) {
	@GetMapping("meal")
	@Operation(summary = "급식 조회", description = "조회할 날짜와 시간대를 받아서 급식표를 반환합니다.")
	@ApiResponses(
		value = [
			ApiResponse(
				responseCode = "200",
				description = "성공적으로 급식표를 조회하였습니다.",
				content = [
					Content(
						mediaType = "application/json",
						schema = Schema(implementation = FetchMealInfoResponse::class),
					),
				],

			),
		],
	)
	fun fetchMeal(
		@Parameter(description = "조회할 날짜", example = "2007-03-27", required = true) @RequestParam date: LocalDate?,
		@Parameter(description = "급식 시간대", example = "LUNCH", required = true) @RequestParam time: LunchTime,
	): ResponseEntity<FetchMealInfoResponse> = fetchMealInfoUsecase.execute(date, time).let { ResponseEntity.ok(it) }

	@GetMapping("timetable")
	fun fetchTimetable(
		@RequestParam date: LocalDate,
		@RequestParam grade: Int,
		@RequestParam classroom: Int,
	): ResponseEntity<FetchTimetableResponse> =
		fetchTimetableUsecase
			.execute(
				FetchTimetableRequest(
					date = date,
					grade = grade,
					classroom = classroom,
				),
			).let {
				ResponseEntity.ok(it)
			}
}
