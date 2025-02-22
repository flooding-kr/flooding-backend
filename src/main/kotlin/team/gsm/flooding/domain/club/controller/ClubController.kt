package team.gsm.flooding.domain.club.controller

import jakarta.validation.Valid
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
import team.gsm.flooding.domain.club.dto.request.ApplyClubRequest
import team.gsm.flooding.domain.club.dto.request.CreateClubRequest
import team.gsm.flooding.domain.club.dto.request.UpdateClubRequest
import team.gsm.flooding.domain.club.dto.response.FetchClubFilterResponse
import team.gsm.flooding.domain.club.entity.ClubType
import team.gsm.flooding.domain.club.usecase.ApplyClubUsecase
import team.gsm.flooding.domain.club.usecase.CreateClubUsecase
import team.gsm.flooding.domain.club.usecase.FetchClubFilterUsecase
import team.gsm.flooding.domain.club.usecase.RemoveClubMemberUsecase
import team.gsm.flooding.domain.club.usecase.UpdateClubUsecase
import java.util.UUID

@RestController
@RequestMapping("club")
class ClubController(
	private val createClubUsecase: CreateClubUsecase,
	private val fetchClubFilterUsecase: FetchClubFilterUsecase,
	private val removeClubMemberUsecase: RemoveClubMemberUsecase,
	private val updateClubUsecase: UpdateClubUsecase,
	private val applyClubUsecase: ApplyClubUsecase,
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
	): ResponseEntity<FetchClubFilterResponse> =
		fetchClubFilterUsecase.execute(type).run {
			ResponseEntity.ok(this)
		}

	@DeleteMapping("{clubId}/member/{userId}")
	fun removeClubMember(
		@PathVariable clubId: UUID,
		@PathVariable userId: UUID,
	): ResponseEntity<Unit> =
		removeClubMemberUsecase.execute(clubId, userId).run {
			ResponseEntity.ok().build()
		}

	@PatchMapping("{clubId}")
	fun updateClubInfo(
		@RequestBody updateClubRequest: UpdateClubRequest,
		@PathVariable clubId: UUID,
	): ResponseEntity<Unit> =
		updateClubUsecase.execute(updateClubRequest.copy(clubId = clubId)).run {
			ResponseEntity.ok().build()
		}

	@PostMapping("applicant")
	fun applyClub(
		@Valid @RequestBody applyClubRequest: ApplyClubRequest,
	): ResponseEntity<Unit> =
		applyClubUsecase.execute(applyClubRequest).run {
			ResponseEntity.ok().build()
		}
}
