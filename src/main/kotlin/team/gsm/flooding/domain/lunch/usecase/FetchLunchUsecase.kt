package team.gsm.flooding.domain.lunch.usecase

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import team.gsm.flooding.domain.lunch.controller.dto.request.LunchTime
import team.gsm.flooding.domain.lunch.controller.dto.response.FetchLunchClientResponse
import team.gsm.flooding.domain.lunch.controller.dto.response.FetchLunchResponse
import team.gsm.flooding.global.exception.ExceptionEnum
import team.gsm.flooding.global.exception.HttpException
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Service
class FetchLunchUsecase(
	@Value("\${lunch-api.key}") private val mealApiKey: String,
) {
	fun execute(
		date: LocalDate,
		lunchTime: LunchTime,
	): FetchLunchResponse {
		val response = getLunchResponse(date, lunchTime)
		if (response?.mealServiceDietInfo == null) {
			throw HttpException(ExceptionEnum.NOT_FOUND_LUNCH)
		}

		val row = response.mealServiceDietInfo[1].row ?: emptyList()
		val menu =
			row[0]
				.ddishNm
				.split("<br/>")
				.map {
					it
						.replace("""\s*\(.*?\)""".toRegex(), "")
						.replace("""[^\w가-힣]""".toRegex(), "")
				}.toList()

		return FetchLunchResponse(menu)
	}

	fun getLunchResponse(
		date: LocalDate,
		lunchTime: LunchTime,
	): FetchLunchClientResponse? {
		val webClient = WebClient.builder().build()
		val dateFormat = DateTimeFormatter.ofPattern("yyyyMMdd")

		return webClient
			.get()
			.uri {
				it
					.scheme("https")
					.host("open.neis.go.kr")
					.path("/hub/mealServiceDietInfo")
					.queryParam("KEY", mealApiKey)
					.queryParam("Type", "json")
					.queryParam("SD_SCHUL_CODE", "7380292")
					.queryParam("MMEAL_SC_CODE", lunchTime.number)
					.queryParam("ATPT_OFCDC_SC_CODE", "F10")
					.queryParam("MLSV_YMD", date.format(dateFormat))
					.build(true)
			}.retrieve()
			.bodyToMono(FetchLunchClientResponse::class.java)
			.block()
	}
}
