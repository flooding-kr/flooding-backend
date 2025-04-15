package kr.flooding.backend.domain.club.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import kr.flooding.backend.domain.club.dto.request.CreateClubRequest
import kr.flooding.backend.domain.club.dto.request.UpdateClubRequest
import kr.flooding.backend.domain.club.dto.response.FetchClubFilterResponse
import kr.flooding.backend.domain.club.dto.response.FetchClubMyselfResponse
import kr.flooding.backend.domain.club.dto.response.FetchClubResponse
import kr.flooding.backend.domain.club.persistence.entity.ClubType
import kr.flooding.backend.domain.club.usecase.CloseClubUsecase
import kr.flooding.backend.domain.club.usecase.ConfirmClubInviteUsecase
import kr.flooding.backend.domain.club.usecase.CreateClubUsecase
import kr.flooding.backend.domain.club.usecase.FetchClubFilterUsecase
import kr.flooding.backend.domain.club.usecase.FetchClubMyselfUsecase
import kr.flooding.backend.domain.club.usecase.FetchClubUsecase
import kr.flooding.backend.domain.club.usecase.InviteClubMemberUsecase
import kr.flooding.backend.domain.club.usecase.OpenClubUsecase
import kr.flooding.backend.domain.club.usecase.RemoveClubMemberUsecase
import kr.flooding.backend.domain.club.usecase.RemoveClubUsecase
import kr.flooding.backend.domain.club.usecase.UpdateClubUsecase
import kr.flooding.backend.domain.club.usecase.WithdrawClubUsecase
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
import java.util.UUID

@Tag(name = "Club", description = "동아리")
@RestController
@RequestMapping("club")
class ClubController(
	private val createClubUsecase: CreateClubUsecase,
	private val fetchClubFilterUsecase: FetchClubFilterUsecase,
	private val fetchClubMyselfUsecase: FetchClubMyselfUsecase,
	private val removeClubMemberUsecase: RemoveClubMemberUsecase,
	private val updateClubUsecase: UpdateClubUsecase,
	private val openClubUsecase: OpenClubUsecase,
	private val closeClubUsecase: CloseClubUsecase,
	private val removeClubUsecase: RemoveClubUsecase,
	private val withdrawClubUsecase: WithdrawClubUsecase,
	private val inviteClubMemberUsecase: InviteClubMemberUsecase,
	private val fetchClubUsecase: FetchClubUsecase,
	private val confirmClubInviteUsecase: ConfirmClubInviteUsecase,
) {
	@Operation(summary = "동아리 생성")
	@PostMapping
	fun createClub(
		@Valid @RequestBody createClubRequest: CreateClubRequest,
	): ResponseEntity<Unit> =
		createClubUsecase.execute(createClubRequest).run {
			ResponseEntity.ok().build()
		}

	@Operation(summary = "동아리 필터 조회")
	@GetMapping
	fun findClubFilter(
		@RequestParam(required = false) type: ClubType?,
	): ResponseEntity<FetchClubFilterResponse> =
		fetchClubFilterUsecase.execute(type).run {
			ResponseEntity.ok(this)
		}

	@Operation(summary = "소속된 동아리 조회")
	@GetMapping("myself")
	fun findClubMyself(): ResponseEntity<FetchClubMyselfResponse> =
		fetchClubMyselfUsecase.execute().run {
			ResponseEntity.ok(this)
		}

	@Operation(summary = "동아리 구성원 추방")
	@DeleteMapping("{clubId}/member/{userId}")
	fun removeClubMember(
		@PathVariable clubId: UUID,
		@PathVariable userId: UUID,
	): ResponseEntity<Unit> =
		removeClubMemberUsecase.execute(clubId, userId).run {
			ResponseEntity.ok().build()
		}

	@Operation(summary = "동아리 정보 수정")
	@PatchMapping("{clubId}")
	fun updateClubInfo(
		@RequestBody updateClubRequest: UpdateClubRequest,
		@PathVariable clubId: UUID,
	): ResponseEntity<Unit> =
		updateClubUsecase.execute(updateClubRequest.copy(clubId = clubId)).run {
			ResponseEntity.ok().build()
		}

	@Operation(summary = "동아리 모집 열기")
	@PostMapping("{clubId}/open")
	fun openClub(
		@PathVariable clubId: UUID,
	): ResponseEntity<Unit> =
		openClubUsecase.execute(clubId).run {
			ResponseEntity.ok().build()
		}

	@Operation(summary = "동아리 모집 닫기")
	@PostMapping("{clubId}/close")
	fun closeClub(
		@PathVariable clubId: UUID,
	): ResponseEntity<Unit> =
		closeClubUsecase.execute(clubId).run {
			ResponseEntity.ok().build()
		}

	@Operation(summary = "동아리 삭제")
	@DeleteMapping("{clubId}")
	fun removeClub(
		@PathVariable clubId: UUID,
	): ResponseEntity<Unit> =
		removeClubUsecase.execute(clubId).run {
			ResponseEntity.noContent().build()
		}

	@Operation(summary = "동아리 탈퇴")
	@DeleteMapping("{clubId}/member")
	fun withdrawClub(
		@PathVariable clubId: UUID,
	): ResponseEntity<Unit> =
		withdrawClubUsecase.execute(clubId).run {
			ResponseEntity.noContent().build()
		}

	@Operation(summary = "동아리 구성원 초대")
	@PostMapping("{clubId}/member/{userId}")
	fun inviteClubMember(
		@PathVariable clubId: UUID,
		@PathVariable userId: UUID,
	): ResponseEntity<Unit> =
		inviteClubMemberUsecase.execute(clubId, userId).run {
			ResponseEntity.ok().build()
		}

	@Operation(summary = "동아리 초대 수락")
	@PostMapping("invite/confirm")
	fun confirmInviteClub(
		@RequestParam code: String,
	): ResponseEntity<Unit> =
		confirmClubInviteUsecase
			.execute(code)
			.run {
				ResponseEntity.ok().build()
			}

	@Operation(summary = "동아리 단일 조회")
	@GetMapping("{clubId}")
	fun fetchClub(
		@PathVariable clubId: UUID,
	): ResponseEntity<FetchClubResponse> =
		fetchClubUsecase.execute(clubId).run {
			ResponseEntity.ok(this)
		}
}
