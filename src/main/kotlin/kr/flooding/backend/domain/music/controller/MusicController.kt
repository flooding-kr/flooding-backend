package kr.flooding.backend.domain.music.controller

import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import kr.flooding.backend.domain.music.dto.request.CreateMusicRequest
import kr.flooding.backend.domain.music.usecase.CreateMusicUsecase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Music", description = "기상 음악")
@RestController
@RequestMapping("/music")
class MusicController(
	private val createMusicUsecase: CreateMusicUsecase,
) {
	@PostMapping
	fun createMusic(
		@Valid @RequestBody createMusicRequest: CreateMusicRequest,
	): ResponseEntity<Unit> =
		createMusicUsecase.execute(createMusicRequest).run {
			ResponseEntity.ok().build()
		}
}
