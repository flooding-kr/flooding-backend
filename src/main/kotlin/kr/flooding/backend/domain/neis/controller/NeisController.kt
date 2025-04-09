package kr.flooding.backend.domain.neis.controller

import io.swagger.v3.oas.annotations.Operation
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
	@Operation(summary = "급식 조회")
	@GetMapping("meal")
	fun fetchMeal(
		@RequestParam date: LocalDate?,
		@RequestParam time: LunchTime,
	): ResponseEntity<FetchMealInfoResponse> = fetchMealInfoUsecase.execute(date, time).let { ResponseEntity.ok(it) }

	@Operation(summary = "시간표 조회")
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
