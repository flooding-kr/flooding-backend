package team.gsm.flooding.domain.neis.model

import com.fasterxml.jackson.annotation.JsonProperty

class TimetableHead(
	val listTotalCount: Int?,
	@JsonProperty("RESULT")
	val result: Result?,
) {
	class Result(
		@JsonProperty("CODE")
		val code: String,
		@JsonProperty("MESSAGE")
		val message: String,
	)
}
