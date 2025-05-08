package kr.flooding.backend.domain.notice.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import kr.flooding.backend.domain.notice.controller.dto.web.response.FetchNoticesResponse
import kr.flooding.backend.domain.notice.usecase.FetchNoticesUsecase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Notice", description = "공지")
@RestController
@RequestMapping("/notice")
class NoticeController(
    private val fetchNoticesUsecase: FetchNoticesUsecase
) {
    @Operation(summary = "공지 조회")
    @GetMapping
    fun fetchNotices(): ResponseEntity<FetchNoticesResponse> =
        fetchNoticesUsecase.execute().let {
            ResponseEntity.ok(it)
        }
}