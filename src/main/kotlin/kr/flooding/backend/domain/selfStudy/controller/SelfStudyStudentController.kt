package kr.flooding.backend.domain.selfStudy.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import kr.flooding.backend.domain.selfStudy.dto.web.request.FetchSelfStudyRequest
import kr.flooding.backend.domain.selfStudy.dto.web.response.FetchSelfStudyListResponse
import kr.flooding.backend.domain.selfStudy.dto.web.response.SelfStudyStatusResponse
import kr.flooding.backend.domain.selfStudy.usecase.CancelSelfStudyUsecase
import kr.flooding.backend.domain.selfStudy.usecase.FetchSelfStudyStudentUsecase
import kr.flooding.backend.domain.selfStudy.usecase.ReserveSelfStudyUsecase
import kr.flooding.backend.domain.selfStudy.usecase.SelfStudyStatusUsecase
import kr.flooding.backend.domain.user.enums.Gender
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@Tag(name = "Self Study", description = "자습")
@RestController
@RequestMapping("/self-study")
class SelfStudyStudentController(
    private val reserveSelfStudyUsecase: ReserveSelfStudyUsecase,
    private val cancelSelfStudyUsecase: CancelSelfStudyUsecase,
    private val selfStudyStatusUsecase: SelfStudyStatusUsecase,
    private val fetchSelfStudyStudentUsecase: FetchSelfStudyStudentUsecase,
) {
    @Operation(summary = "자습 신청")
    @PostMapping
    fun reserveSelfStudy(): ResponseEntity<Unit> =
        reserveSelfStudyUsecase.execute().let {
            ResponseEntity.ok().build()
        }

    @Operation(summary = "자습 취소")
    @DeleteMapping
    fun cancelSelfStudy(): ResponseEntity<Unit> =
        cancelSelfStudyUsecase.execute().let {
            ResponseEntity.ok().build()
        }

    @Operation(summary = "자습 현황 조회")
    @GetMapping("/status")
    fun getSelfStudyStatus(): ResponseEntity<SelfStudyStatusResponse> =
        selfStudyStatusUsecase.execute().let {
            ResponseEntity.ok(it)
        }

    @Operation(summary = "자습 신청자 조회")
    @GetMapping("/rank")
    fun getSelfStudyRank(
        @RequestParam(required = false) name: String?,
        @RequestParam(required = false) grade: Int?,
        @RequestParam(required = false) classroom: Int?,
        @RequestParam(required = false) gender: Gender?,
    ): ResponseEntity<FetchSelfStudyListResponse> =
        fetchSelfStudyStudentUsecase
            .execute(
                FetchSelfStudyRequest(
                    name = name,
                    grade = grade,
                    classroom = classroom,
                    gender = gender,
                ),
            ).let {
                ResponseEntity.ok(it)
            }
}
