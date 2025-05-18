package kr.flooding.backend.domain.music.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import kr.flooding.backend.domain.music.dto.web.request.CreateMusicRequest
import kr.flooding.backend.domain.music.enums.MusicOrderType
import kr.flooding.backend.domain.music.dto.web.response.FetchMusicResponse
import kr.flooding.backend.domain.music.dto.web.response.UpdateMusicLikeResponse
import kr.flooding.backend.domain.music.usecase.student.CreateMusicUsecase
import kr.flooding.backend.domain.music.usecase.student.FetchMusicUsecase
import kr.flooding.backend.domain.music.usecase.student.RemoveMusicMyselfUsecase
import kr.flooding.backend.domain.music.usecase.student.UpdateMusicLikeUsecase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate
import java.util.UUID

@Tag(name = "Music", description = "기상 음악")
@RestController
@RequestMapping("music")
class MusicStudentController(
	private val createMusicUsecase: CreateMusicUsecase,
	private val fetchMusicUsecase: FetchMusicUsecase,
	private val updateMusicLikeUsecase: UpdateMusicLikeUsecase,
	private val removeMusicMyselfUsecase: RemoveMusicMyselfUsecase,
) {
	@Operation(summary = "기상 음악 신청")
	@PostMapping
	fun createMusic(
		@Valid @RequestBody createMusicRequest: CreateMusicRequest,
	): ResponseEntity<Unit> =
		createMusicUsecase.execute(createMusicRequest).run {
			ResponseEntity.ok().build()
		}

	@Operation(summary = "기상 음악 조회")
	@GetMapping
	fun fetchMusic(
		@RequestParam date: LocalDate,
		@RequestParam orderType: MusicOrderType,
	): ResponseEntity<FetchMusicResponse> =
		fetchMusicUsecase.execute(date, orderType).run {
			ResponseEntity.ok(this)
		}

	@Operation(summary = "기상 음악 본인 삭제")
	@DeleteMapping
	fun removeMusic(): ResponseEntity<Unit> =
		removeMusicMyselfUsecase.execute().run {
			ResponseEntity.ok().build()
		}

	@Operation(summary = "기상 음악 좋아요")
	@PatchMapping("/{musicId}/like")
	fun updateMusicLike(
		@PathVariable musicId: UUID,
	): ResponseEntity<UpdateMusicLikeResponse> =
		updateMusicLikeUsecase.execute(musicId).run {
			ResponseEntity.ok(this)
		}
}
