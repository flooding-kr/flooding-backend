package kr.flooding.backend.domain.club.dto.request

import java.util.UUID

class CreateRecruitmentFormRequest(
	val clubId: UUID,

	val questions: List<String>,
)
