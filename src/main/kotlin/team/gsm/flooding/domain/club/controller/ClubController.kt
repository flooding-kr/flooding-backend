package team.gsm.flooding.domain.club.controller

import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import team.gsm.flooding.domain.club.dto.request.CreateClubRequest
import team.gsm.flooding.domain.club.usecase.CreateClubUsecase

@RestController
@RequestMapping("club")
class ClubController(
	private val createClubUsecase: CreateClubUsecase,
) {
	@PostMapping
	fun createClub(
		@Valid @RequestBody createClubRequest: CreateClubRequest,
	): ResponseEntity<Unit> =
		createClubUsecase.execute(createClubRequest).run {
			ResponseEntity.ok().build()
		}
}
