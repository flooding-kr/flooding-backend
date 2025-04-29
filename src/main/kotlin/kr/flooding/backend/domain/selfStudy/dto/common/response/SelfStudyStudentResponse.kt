package kr.flooding.backend.domain.selfStudy.dto.common.response

import kr.flooding.backend.domain.user.persistence.entity.User

class SelfStudyStudentResponse(
    val studentNumber: String,
    val name: String,
    val profileImageUrl: String?,
) {
    companion object {
        fun toDto(
            student: User,
            profileImageUrl: String?
        ): SelfStudyStudentResponse {
            val studentInfo = requireNotNull(student.studentInfo) { "학생 정보가 없습니다." }

            return SelfStudyStudentResponse(
                studentNumber = studentInfo.toSchoolNumber(),
                name = student.name,
                profileImageUrl = profileImageUrl
            )
        }
    }
}