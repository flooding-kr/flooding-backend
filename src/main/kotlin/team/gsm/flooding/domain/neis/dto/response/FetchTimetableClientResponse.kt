package team.gsm.flooding.domain.neis.dto.response

import com.fasterxml.jackson.annotation.JsonProperty
import team.gsm.flooding.domain.neis.model.Head
import team.gsm.flooding.domain.neis.model.TimetableRow

class FetchTimetableClientResponse(
	@JsonProperty("hisTimetable")
	val hisTimetable: List<MealServiceDietInfo>?,
) {
	class MealServiceDietInfo(
		val head: List<Head>?,
		val row: List<TimetableRow>?,
	)
}
