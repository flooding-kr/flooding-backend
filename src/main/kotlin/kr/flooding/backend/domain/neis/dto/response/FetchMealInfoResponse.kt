package kr.flooding.backend.domain.neis.dto.response

data class FetchMealInfoResponse(
	val menu: List<String>,
	val kcal: Double,
)
