package kr.flooding.backend.domain.neis.model

import com.fasterxml.jackson.annotation.JsonProperty

class Head(
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
