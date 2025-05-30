package kr.flooding.backend.domain.club.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import kr.flooding.backend.domain.club.dto.web.request.CreateClubRequest
import kr.flooding.backend.domain.club.dto.web.request.UpdateClubRequest
import kr.flooding.backend.domain.club.dto.web.response.FetchClubFilterResponse
import kr.flooding.backend.domain.club.dto.web.response.FetchClubMyselfResponse
import kr.flooding.backend.domain.club.dto.web.response.FetchClubResponse
import kr.flooding.backend.domain.club.enums.ClubType
import kr.flooding.backend.domain.club.usecase.student.CloseClubUsecase
import kr.flooding.backend.domain.club.usecase.student.ConfirmClubInviteUsecase
import kr.flooding.backend.domain.club.usecase.student.CreateClubUsecase
import kr.flooding.backend.domain.club.usecase.student.FetchClubFilterUsecase
import kr.flooding.backend.domain.club.usecase.student.FetchClubMyselfUsecase
import kr.flooding.backend.domain.club.usecase.student.FetchClubUsecase
import kr.flooding.backend.domain.club.usecase.student.InviteClubMemberUsecase
import kr.flooding.backend.domain.club.usecase.student.OpenClubUsecase
import kr.flooding.backend.domain.club.usecase.student.RemoveClubMemberUsecase
import kr.flooding.backend.domain.club.usecase.student.RemoveClubUsecase
import kr.flooding.backend.domain.club.usecase.student.UpdateClubUsecase
import kr.flooding.backend.domain.club.usecase.student.WithdrawClubUsecase
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
