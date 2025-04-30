package kr.flooding.backend.domain.selfStudy.dto.common.response

import kr.flooding.backend.domain.file.shared.PresignedUrlModel
import kr.flooding.backend.domain.selfStudy.persistence.entity.SelfStudyReservation
import java.util.UUID

class FetchSelfStudyResponse(
    val id: UUID?,
    val student: SelfStudyStudentResponse
) {
    companion object {
        fun toDto(
            selfStudyReservation: SelfStudyReservation,
            profileImage: PresignedUrlModel?,
        ): FetchSelfStudyResponse =
            FetchSelfStudyResponse(
                id = selfStudyReservation.id,
                student = SelfStudyStudentResponse.toDto(selfStudyReservation.student, profileImage)
            )
    }
}
