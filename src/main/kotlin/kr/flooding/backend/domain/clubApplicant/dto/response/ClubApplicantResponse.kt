package kr.flooding.backend.domain.clubApplicant.dto.response

import java.util.UUID

data class ClubApplicantResponse(
	val applicantId: UUID,
	val userId: UUID,
	val name: String,
)
