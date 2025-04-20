package kr.flooding.backend.domain.neis.usecase

import kr.flooding.backend.domain.neis.enums.LunchTime
import kr.flooding.backend.domain.neis.dto.response.FetchMealInfoClientResponse
import kr.flooding.backend.domain.neis.dto.response.FetchMealInfoResponse
import kr.flooding.backend.global.exception.ExceptionEnum
import kr.flooding.backend.global.exception.HttpException
import kr.flooding.backend.global.exception.toPair
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
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
			throw HttpException(ExceptionEnum.NEIS.NOT_FOUND_LUNCH.toPair())
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
		val kcal =
			row[0]
				.calInfo
				.split(' ')[0]
				.toDouble()

		return FetchMealInfoResponse(
			menu = menu,
			kcal = kcal,
		)
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
