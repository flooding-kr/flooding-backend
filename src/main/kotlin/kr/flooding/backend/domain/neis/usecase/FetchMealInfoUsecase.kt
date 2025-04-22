package kr.flooding.backend.domain.neis.usecase

import kr.flooding.backend.domain.neis.enums.LunchTime
import kr.flooding.backend.domain.neis.dto.response.FetchMealInfoClientResponse
import kr.flooding.backend.domain.neis.dto.response.FetchMealInfoResponse
import kr.flooding.backend.global.exception.ExceptionEnum
import kr.flooding.backend.global.exception.HttpException
import kr.flooding.backend.global.exception.toPair
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Service
class FetchMealInfoUsecase(
	@Value("\${neis.api-key}") private val neisApiKey: String,
) {
	fun execute(
        date: LocalDate?,
        lunchTime: LunchTime,
	): FetchMealInfoResponse {
		val requestDate = date ?: LocalDate.now()
		val response = getMealInfoResponse(requestDate, lunchTime)

		val mealServiceDietInfo =
			if(response?.mealServiceDietInfo?.isNotEmpty() == true) response.mealServiceDietInfo[1]
			else null

		val menu = mealServiceDietInfo?.row?.let {
			it[0].ddishNm
				.split("<br/>")
				.map { it
					.replace("""\s*\(.*?\)""".toRegex(), "")
					.replace("""[^\w가-힣]""".toRegex(), "")
				}.toList()
		} ?: emptyList()

		val kcal = mealServiceDietInfo?.row?.let {
			it[0].calInfo
				.split(' ')[0]
				.toDouble()
		} ?: 0.0

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
					.queryParam("KEY", neisApiKey)
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
