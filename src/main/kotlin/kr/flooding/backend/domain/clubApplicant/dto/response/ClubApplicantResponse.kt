package kr.flooding.backend.domain.clubApplicant.dto.response

import kr.flooding.backend.domain.file.shared.PresignedUrlModel
import kr.flooding.backend.domain.user.shared.StudentInfoModel
import java.util.UUID

data class ClubApplicantResponse(
	val applicantId: UUID,
	val userId: UUID,
	val name: String,
	val studentInfo: StudentInfoModel?,
	val profileImage: PresignedUrlModel?,
)
