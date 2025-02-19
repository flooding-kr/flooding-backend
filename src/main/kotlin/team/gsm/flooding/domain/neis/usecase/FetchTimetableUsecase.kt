package team.gsm.flooding.domain.neis.usecase

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import team.gsm.flooding.domain.neis.dto.response.FetchTimetableClientResponse
import team.gsm.flooding.domain.neis.dto.response.FetchTimetableResponse
import team.gsm.flooding.global.exception.ExceptionEnum
import team.gsm.flooding.global.exception.HttpException
import team.gsm.flooding.global.util.StudentUtil.Companion.calcYearToGrade
import team.gsm.flooding.global.util.UserUtil
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Service
class FetchTimetableUsecase(
	@Value("\${lunch-api.key}") private val mealApiKey: String,
	private val userUtil: UserUtil,
) {
	fun execute(date: LocalDate?): FetchTimetableResponse {
		val requestDate = date ?: LocalDate.now()
		val currentUser = userUtil.getUser()
		val grade = calcYearToGrade(currentUser.studentInfo.year)

		val response =
			getTimetableResponse(
				date = requestDate,
				grade = grade,
				classroom = currentUser.studentInfo.classroom,
			)
		if (response?.hisTimetable == null) {
			throw HttpException(ExceptionEnum.NOT_FOUND_LUNCH)
		}

		val row =
			response
				.hisTimetable[1]
				.row
				?.distinctBy {
					it.perio
				}?.map { it.itrtCntnt }
				?.toList() ?: emptyList()

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
