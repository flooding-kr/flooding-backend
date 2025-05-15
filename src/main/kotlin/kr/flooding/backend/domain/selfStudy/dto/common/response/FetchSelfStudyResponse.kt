package kr.flooding.backend.domain.selfStudy.dto.common.response

import kr.flooding.backend.domain.file.shared.PresignedUrlModel
import kr.flooding.backend.domain.selfStudy.persistence.entity.SelfStudyReservation
import java.util.UUID

class FetchSelfStudyResponse(
    val id: UUID,
    val studentNumber: String,
    val profileImage: PresignedUrlModel?,
    val name: String,
    val isPresent: Boolean,
) {
    companion object {
        fun toDto(
            reservation: SelfStudyReservation,
            profileImage: PresignedUrlModel?,
        ): FetchSelfStudyResponse {
            val studentInfo = requireNotNull(reservation.student.studentInfo)
            val id = checkNotNull(reservation.id) { "Reservation ID must not be null." }

            return FetchSelfStudyResponse(
                id = id,
                studentNumber = studentInfo.toSchoolNumber(),
                profileImage = profileImage,
                name = reservation.student.name,
                isPresent = reservation.isPresent
            )
        }
    }
}
