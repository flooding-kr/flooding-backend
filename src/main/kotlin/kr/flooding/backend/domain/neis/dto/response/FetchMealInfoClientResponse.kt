package kr.flooding.backend.domain.neis.dto.response

import com.fasterxml.jackson.annotation.JsonProperty
import kr.flooding.backend.domain.neis.model.Head
import kr.flooding.backend.domain.neis.model.MealInfoRow

class FetchMealInfoClientResponse(
	@JsonProperty("mealServiceDietInfo")
	val mealServiceDietInfo: List<MealServiceDietInfo>?,
) {
	class MealServiceDietInfo(
		val head: List<Head>?,
		val row: List<MealInfoRow>?,
	)
}
