package kr.flooding.backend.domain.neis.dto.response

import com.fasterxml.jackson.annotation.JsonProperty
import kr.flooding.backend.domain.neis.model.Head
import kr.flooding.backend.domain.neis.model.TimetableRow

class FetchTimetableClientResponse(
	@JsonProperty("hisTimetable")
	val hisTimetable: List<MealServiceDietInfo>?,
) {
	class MealServiceDietInfo(
		val head: List<Head>?,
		val row: List<TimetableRow>?,
	)
}
