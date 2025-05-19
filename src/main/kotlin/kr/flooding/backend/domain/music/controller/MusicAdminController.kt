package kr.flooding.backend.domain.music.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import kr.flooding.backend.domain.music.usecase.admin.RemoveMusicUsecase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@Tag(name = "Music Management", description = "기상 음악 관리")
@RestController
@RequestMapping("/admin/music")
class MusicAdminController(
	private val removeMusicUsecase: RemoveMusicUsecase,
) {
	@Operation(summary = "기상 음악 삭제")
	@DeleteMapping("/{musicId}")
	fun removeMusicById(@PathVariable musicId: UUID): ResponseEntity<Unit> =
		removeMusicUsecase.execute(musicId).run {
			ResponseEntity.ok().build()
		}
}
