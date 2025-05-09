package kr.flooding.backend.domain.selfStudy.usecase

import kr.flooding.backend.domain.selfStudy.dto.common.response.FetchSelfStudyResponse
import kr.flooding.backend.domain.selfStudy.dto.web.request.FetchSelfStudyRequest
import kr.flooding.backend.domain.selfStudy.dto.web.response.FetchSelfStudyListResponse
import kr.flooding.backend.domain.selfStudy.persistence.repository.jdsl.SelfStudyReservationJdslRepository
import kr.flooding.backend.global.thirdparty.s3.adapter.S3Adapter
import kr.flooding.backend.global.util.DateUtil
import kr.flooding.backend.global.util.StudentUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class FetchSelfStudyStudentUsecase(
    private val selfStudyReservationJdslRepository: SelfStudyReservationJdslRepository,
    private val s3Adapter: S3Adapter,
) {
    fun execute(request: FetchSelfStudyRequest): FetchSelfStudyListResponse {

        val year = request.grade?.let { StudentUtil.calcGradeToYear(it) }

        val reservations = selfStudyReservationJdslRepository
            .findByCreatedByBetweenAndYearAndClassroomAndGenderAndNameLikesAndIsCancelledFalse(
                createdAtBefore = DateUtil.getAtStartOfToday(),
                createdAtAfter = DateUtil.getAtEndOfToday(),
                year = year,
                classroom = request.classroom,
                gender = request.gender,
                name = request.name,
            )

        val responses = reservations.map { reservation ->
            FetchSelfStudyResponse.toDto(
                selfStudyReservation = reservation,
                profileImage = reservation.student.profileImageKey?.let {
                    s3Adapter.generatePresignedUrl(it)
                },
            )
        }

        return FetchSelfStudyListResponse(responses)
    }
}
