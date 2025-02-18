package team.gsm.flooding.domain.neis.dto.response

import com.fasterxml.jackson.annotation.JsonProperty
import team.gsm.flooding.domain.neis.model.Head
import team.gsm.flooding.domain.neis.model.MealInfoRow

class FetchMealInfoClientResponse(
	@JsonProperty("mealServiceDietInfo")
	val mealServiceDietInfo: List<MealServiceDietInfo>?,
) {
	class MealServiceDietInfo(
		val head: List<Head>?,
		val row: List<MealInfoRow>?,
	)
}
