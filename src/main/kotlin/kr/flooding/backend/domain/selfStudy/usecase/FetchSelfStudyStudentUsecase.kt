package kr.flooding.backend.domain.selfStudy.usecase

import kr.flooding.backend.domain.selfStudy.dto.request.FetchSelfStudyRequest
import kr.flooding.backend.domain.selfStudy.dto.response.FetchSelfStudyListResponse
import kr.flooding.backend.domain.selfStudy.dto.response.FetchSelfStudyResponse
import kr.flooding.backend.domain.selfStudy.persistence.repository.jpa.SelfStudyReservationJpaRepository
import kr.flooding.backend.global.thirdparty.s3.adapter.S3Adapter
import kr.flooding.backend.global.util.DateUtil
import kr.flooding.backend.global.util.StudentUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class FetchSelfStudyStudentUsecase(
    private val selfStudyReservationjpaRepository: SelfStudyReservationJpaRepository,
    private val s3Adpter: S3Adapter,
) {
    fun execute(request: FetchSelfStudyRequest): FetchSelfStudyListResponse {
        val reservations =
            selfStudyReservationjpaRepository
                .findByCreatedAtBetween(
                    createdAtBefore = DateUtil.getAtStartOfToday(),
                    createdAtAfter = DateUtil.getAtEndOfToday(),
                ).map {
                    val studentInfo = requireNotNull(it.student.studentInfo) { "학생 정보가 없습니다." }

                    val year = requireNotNull(studentInfo.year)
                    val classroom = requireNotNull(studentInfo.classroom)
                    val number = requireNotNull(studentInfo.number)

                    FetchSelfStudyResponse(
                        studentNumber = StudentUtil.calcStudentNumber(year, classroom, number),
                        name = it.student.name,
                        profileImageUrl =
                            it.student.profileImageKey?.let {
                                s3Adpter.generatePresignedUrl(it)
                            },
                    )
                }

        return FetchSelfStudyListResponse(reservations)
    }
}
