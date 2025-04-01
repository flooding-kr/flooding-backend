package kr.flooding.backend.domain.neis.dto.request

import java.time.LocalDate

data class FetchTimetableRequest(
	val date: LocalDate,
	val grade: Int,
	val classroom: Int,
)
