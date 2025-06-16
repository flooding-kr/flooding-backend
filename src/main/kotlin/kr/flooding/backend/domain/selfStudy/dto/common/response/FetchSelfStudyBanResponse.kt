package kr.flooding.backend.domain.selfStudy.dto.common.response

import kr.flooding.backend.domain.file.shared.PresignedUrlModel
import kr.flooding.backend.domain.selfStudy.persistence.entity.SelfStudyBan
import java.util.UUID

data class FetchSelfStudyBanResponse(
    val id: UUID,
    val studentNumber: String,
    val profileImage: PresignedUrlModel?,
    val name: String,
) {
    companion object {
        fun toDto(
            selfStudyBan: SelfStudyBan,
            profileImage: PresignedUrlModel?,
        ): FetchSelfStudyBanResponse {
            val studentInfo = requireNotNull(selfStudyBan.student.studentInfo)
            val id = checkNotNull(selfStudyBan.id) { "SelfStudyBan ID must not be null." }

            return FetchSelfStudyBanResponse(
                id = id,
                studentNumber = studentInfo.toSchoolNumber(),
                profileImage = profileImage,
                name = selfStudyBan.student.name,
            )
        }
    }
}