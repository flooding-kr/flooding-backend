package team.gsm.flooding.domain.attendance.dto.request

import java.util.UUID

class UpdateHomebaseTableRequest (
	val participants: MutableList<UUID>,
)