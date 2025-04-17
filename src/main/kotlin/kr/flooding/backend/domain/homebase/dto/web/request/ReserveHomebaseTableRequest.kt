package kr.flooding.backend.domain.homebase.dto.web.request

import jakarta.validation.constraints.NotEmpty
import java.util.UUID

class ReserveHomebaseTableRequest(
	val tableNumber: Int,
	val floor: Int,
	val participants: List<UUID>,
	val period: Int,
	@field:NotEmpty(message = "홈베이스 신청 사유를 입력해주세요.")
	val reason: String,
)
