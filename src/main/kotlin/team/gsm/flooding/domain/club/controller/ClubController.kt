package team.gsm.flooding.domain.club.controller

import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import team.gsm.flooding.domain.club.dto.request.CreateClubRequest
import team.gsm.flooding.domain.club.dto.response.FindClubFilterResponse
import team.gsm.flooding.domain.club.entity.ClubType
import team.gsm.flooding.domain.club.usecase.CreateClubUsecase
import team.gsm.flooding.domain.club.usecase.FindClubFilterUsecase

@RestController
@RequestMapping("club")
class ClubController(
	private val createClubUsecase: CreateClubUsecase,
	private val findClubFilterUsecase: FindClubFilterUsecase,
) {
	@PostMapping
	fun createClub(
		@Valid @RequestBody createClubRequest: CreateClubRequest,
	): ResponseEntity<Unit> =
		createClubUsecase.execute(createClubRequest).run {
			ResponseEntity.ok().build()
		}

	@GetMapping
	fun findClubFilter(
		@RequestParam type: ClubType,
	): ResponseEntity<FindClubFilterResponse> =
		findClubFilterUsecase.execute(type).run {
			ResponseEntity.ok(this)
		}
}
