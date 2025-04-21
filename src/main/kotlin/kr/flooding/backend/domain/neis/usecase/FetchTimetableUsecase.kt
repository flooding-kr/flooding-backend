package kr.flooding.backend.domain.neis.usecase

import kr.flooding.backend.domain.neis.dto.request.FetchTimetableRequest
import kr.flooding.backend.domain.neis.dto.response.FetchTimetableClientResponse
import kr.flooding.backend.domain.neis.dto.response.FetchTimetableResponse
import kr.flooding.backend.global.exception.ExceptionEnum
import kr.flooding.backend.global.exception.HttpException
import kr.flooding.backend.global.exception.toPair
import kr.flooding.backend.global.util.UserUtil
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Service
class FetchTimetableUsecase(
	@Value("\${lunch-api.key}") private val mealApiKey: String,
) {
	fun execute(request: FetchTimetableRequest): FetchTimetableResponse {
		val response =
			getTimetableResponse(
				date = request.date,
				grade = request.grade,
				classroom = request.classroom,
			)
		val timetable = response?.hisTimetable

		val row =
			if(timetable.isNullOrEmpty()) emptyList()
			else {
				timetable[1].row
					?.distinctBy { it.perio }
					?.map { it.itrtCntnt }
					?.toList() ?: emptyList()
			}

		return FetchTimetableResponse(row)
	}

	fun getTimetableResponse(
		date: LocalDate,
		grade: Int,
		classroom: Int,
	): FetchTimetableClientResponse? {
		val webClient = WebClient.builder().build()
		val dateFormat = DateTimeFormatter.ofPattern("yyyyMMdd")

		return webClient
			.get()
			.uri {
				it
					.scheme("https")
					.host("open.neis.go.kr")
					.path("/hub/hisTimetable")
					.queryParam("KEY", mealApiKey)
					.queryParam("Type", "json")
					.queryParam("SD_SCHUL_CODE", "7380292")
					.queryParam("ATPT_OFCDC_SC_CODE", "F10")
					.queryParam("GRADE", grade)
					.queryParam("CLASS_NM", classroom)
					.queryParam("ALL_TI_YMD", date.format(dateFormat))
					.build(true)
			}.retrieve()
			.bodyToMono(FetchTimetableClientResponse::class.java)
			.block()
	}
}
