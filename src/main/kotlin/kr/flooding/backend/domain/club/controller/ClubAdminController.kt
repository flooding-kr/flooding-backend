package kr.flooding.backend.domain.club.controller

import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import kr.flooding.backend.domain.club.dto.web.request.ApproveClubRequest
import kr.flooding.backend.domain.club.usecase.admin.ApproveClubUsecase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Club Management", description = "동아리 관리")
@RestController
@RequestMapping("/admin/club")
class ClubAdminController(
    private val approveClubUsecase: ApproveClubUsecase,
) {
    @PostMapping("/approve")
    fun approveClub(@Valid @RequestBody request: ApproveClubRequest): ResponseEntity<Unit> =
        approveClubUsecase.execute(request).run {
            ResponseEntity.ok().build()
        }
}