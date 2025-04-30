package kr.flooding.backend.domain.selfStudy.dto.common.response

import kr.flooding.backend.domain.file.shared.PresignedUrlModel
import kr.flooding.backend.domain.user.persistence.entity.User

class SelfStudyStudentResponse(
    val studentNumber: String,
    val name: String,
    val profileImage: PresignedUrlModel?,
) {
    companion object {
        fun toDto(
            student: User,
            profileImage: PresignedUrlModel?
        ): SelfStudyStudentResponse {
            val studentInfo = requireNotNull(student.studentInfo) { "학생 정보가 없습니다." }

            return SelfStudyStudentResponse(
                studentNumber = studentInfo.toSchoolNumber(),
                name = student.name,
                profileImage = profileImage
            )
        }
    }
}