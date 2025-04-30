package kr.flooding.backend.domain.club.dto.common.response

import kr.flooding.backend.domain.file.shared.PresignedUrlModel
import kr.flooding.backend.domain.user.persistence.entity.StudentInfo
import kr.flooding.backend.domain.user.persistence.entity.User
import kr.flooding.backend.domain.user.shared.StudentInfoModel
import java.util.UUID

class ClubStudentResponse(
	val id: UUID,
	val name: String,
	val studentInfo: StudentInfoModel,
	val profileImage: PresignedUrlModel?,
) {
	companion object {
		fun toDto(user: User, presignedUrlModel: PresignedUrlModel?): ClubStudentResponse {
			val studentInfo = requireNotNull(user.studentInfo)
			return ClubStudentResponse(
				id = requireNotNull(user.id),
				name = user.name,
				studentInfo = studentInfo.toModel(),
				profileImage = presignedUrlModel,
			)
		}
	}
}
