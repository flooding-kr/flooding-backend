package kr.flooding.backend.domain.music.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import kr.flooding.backend.domain.music.dto.request.CreateMusicRequest
import kr.flooding.backend.domain.music.dto.response.UpdateMusicLikeResponse
import kr.flooding.backend.domain.music.usecase.CreateMusicUsecase
import kr.flooding.backend.domain.music.usecase.UpdateMusicLikeUsecase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@Tag(name = "Music", description = "기상 음악")
@RestController
@RequestMapping("music")
class MusicController(
	private val createMusicUsecase: CreateMusicUsecase,
	private val updateMusicLikeUsecase: UpdateMusicLikeUsecase,
) {
	@Operation(summary = "기상 음악 신청")
	@PostMapping
	fun createMusic(
		@Valid @RequestBody createMusicRequest: CreateMusicRequest,
	): ResponseEntity<Unit> =
		createMusicUsecase.execute(createMusicRequest).run {
			ResponseEntity.ok().build()
		}

	@Operation(summary = "기상 음악 좋아요")
	@PatchMapping("{musicId}/like")
	fun updateMusicLike(
		@PathVariable musicId: UUID,
	): ResponseEntity<UpdateMusicLikeResponse> =
		updateMusicLikeUsecase.execute(musicId).run {
			ResponseEntity.ok(this)
		}
}
