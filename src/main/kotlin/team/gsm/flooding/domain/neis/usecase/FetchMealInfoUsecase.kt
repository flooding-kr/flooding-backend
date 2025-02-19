package team.gsm.flooding.domain.neis.usecase

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import team.gsm.flooding.domain.neis.dto.request.LunchTime
import team.gsm.flooding.domain.neis.dto.response.FetchMealInfoClientResponse
import team.gsm.flooding.domain.neis.dto.response.FetchMealInfoResponse
import team.gsm.flooding.global.exception.ExceptionEnum
import team.gsm.flooding.global.exception.HttpException
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Service
class FetchMealInfoUsecase(
	@Value("\${lunch-api.key}") private val mealApiKey: String,
) {
	fun execute(
		date: LocalDate?,
		lunchTime: LunchTime,
	): FetchMealInfoResponse {
		val requestDate = date ?: LocalDate.now()
		val response = getMealInfoResponse(requestDate, lunchTime)
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

		return FetchMealInfoResponse(menu)
	}

	fun getMealInfoResponse(
		date: LocalDate,
		lunchTime: LunchTime,
	): FetchMealInfoClientResponse? {
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
			.bodyToMono(FetchMealInfoClientResponse::class.java)
			.block()
	}
}
