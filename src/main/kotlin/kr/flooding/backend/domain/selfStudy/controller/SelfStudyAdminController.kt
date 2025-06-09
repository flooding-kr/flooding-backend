package kr.flooding.backend.domain.selfStudy.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import kr.flooding.backend.domain.selfStudy.dto.web.request.ChangeSelfStudyLimitRequest
import kr.flooding.backend.domain.selfStudy.usecase.*
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.UUID

@Tag(name = "Self Study Management", description = "자습 관리")
@RestController
@RequestMapping("/admin/self-study")
class SelfStudyAdminController(
    private val changeSelfStudyLimitUsecase: ChangeSelfStudyLimitUsecase,
    private val banSelfStudyUsecase: BanSelfStudyUsecase,
    private val attendSelfStudyUsecase: AttendSelfStudyUsecase,
    private val absenceSelfStudyUsecase: AbsenceSelfStudyUsecase,
    private val cancelSelfStudyBanUsecase: CancelSelfStudyBanUsecase,
) {
    @Operation(summary = "자습 최대 인원 변경")
    @PatchMapping("/limit")
    fun changeSelfStudyLimit(
        @RequestBody request: ChangeSelfStudyLimitRequest,
    ): ResponseEntity<Unit> =
        changeSelfStudyLimitUsecase.execute(request).let {
            ResponseEntity.ok().build()
        }

    @Operation(summary = "자습 정지")
    @PostMapping("/{selfStudyReservationId}/ban")
    fun banSelfStudy(
        @PathVariable selfStudyReservationId: UUID
    ): ResponseEntity<Unit> =
        banSelfStudyUsecase.execute(selfStudyReservationId).let {
            ResponseEntity.ok().build()
        }

    @Operation(summary = "자습 정지 취소")
    @DeleteMapping("/{selfStudyReservationId}/ban")
    fun cancelSelfStudyBan(
        @PathVariable selfStudyReservationId: UUID
    ): ResponseEntity<Unit> =
        cancelSelfStudyBanUsecase.execute(selfStudyReservationId).let {
            ResponseEntity.ok().build()
        }

    @Operation(summary = "자습 출석")
    @PatchMapping("/{selfStudyReservationId}/attend")
    fun attendSelfStudy(
        @PathVariable selfStudyReservationId: UUID
    ): ResponseEntity<Unit> =
        attendSelfStudyUsecase.execute(selfStudyReservationId).let {
            ResponseEntity.ok().build()
        }

    @Operation(summary = "자습 결석")
    @PatchMapping("/{selfStudyReservationId}/absence")
    fun absenceSelfStudy(
        @PathVariable selfStudyReservationId: UUID
    ): ResponseEntity<Unit> =
        absenceSelfStudyUsecase.execute(selfStudyReservationId).let {
            ResponseEntity.ok().build()
        }
}
