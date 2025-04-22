package kr.flooding.backend.domain.massage.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import kr.flooding.backend.domain.massage.dto.ChangeMassageLimitRequest
import kr.flooding.backend.domain.massage.usecase.ChangeMassageLimitUsecase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Massage Management", description = "안마의자 관리")
@RestController
@RequestMapping("/admin/massage")
class MassageAdminController(
    private val changeMassageLimitUsecase: ChangeMassageLimitUsecase,
) {
    @Operation(summary = "안마의자 최대 인원 변경")
    @PatchMapping("/limit")
    fun changeMassageLimit(
        @RequestBody request: ChangeMassageLimitRequest
    ): ResponseEntity<Unit> =
        changeMassageLimitUsecase.execute(request).let {
            ResponseEntity.ok().build()
        }
}