package kr.flooding.backend.domain.notice.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import kr.flooding.backend.domain.notice.controller.dto.request.CreateNoticeRequest
import kr.flooding.backend.domain.notice.usecase.CreateNoticeUsecase
import kr.flooding.backend.domain.notice.usecase.RemoveNoticeUsecase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@Tag(name = "Notice Management", description = "공지 관리")
@RestController
@RequestMapping("/admin/notice")
class NoticeAdminController(
    private val createNoticeUsecase: CreateNoticeUsecase,
    private val removeNoticeUsecase: RemoveNoticeUsecase,
) {
    @Operation(summary = "공지 작성")
    @PostMapping
    fun createNotice(@RequestBody request: CreateNoticeRequest): ResponseEntity<Unit> =
        createNoticeUsecase.execute(request).let {
            ResponseEntity.ok().build()
        }

    @Operation(summary = "공지 삭제")
    @DeleteMapping("/{noticeId}")
    fun removeNotice(@PathVariable noticeId: UUID): ResponseEntity<Unit> =
        removeNoticeUsecase.execute(noticeId).let {
            ResponseEntity.ok().build()
        }
}