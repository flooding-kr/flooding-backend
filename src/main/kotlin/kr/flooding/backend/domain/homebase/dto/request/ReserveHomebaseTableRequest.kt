package kr.flooding.backend.domain.homebase.dto.request

import java.util.UUID

class ReserveHomebaseTableRequest(
	val tableNumber: Int,
	val floor: Int,
	val participants: List<UUID>,
	val period: Int,
)
