package kr.flooding.backend.domain.club.controller

import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import jdk.jshell.execution.Util
import kr.flooding.backend.domain.club.dto.request.AbsenceClubLeaderRequest
import kr.flooding.backend.domain.club.dto.request.AbsenceClubMyselfRequest
import kr.flooding.backend.domain.club.dto.request.ApplyClubRequest
import kr.flooding.backend.domain.club.dto.request.AttendClubLeaderRequest
import kr.flooding.backend.domain.club.dto.request.AttendClubMyselfRequest
import kr.flooding.backend.domain.club.dto.request.CreateClubRequest
import kr.flooding.backend.domain.club.dto.request.UpdateClubRequest
import kr.flooding.backend.domain.club.dto.response.FetchClubFilterResponse
import kr.flooding.backend.domain.club.dto.response.FetchClubResponse
import kr.flooding.backend.domain.club.entity.ClubType
import kr.flooding.backend.domain.club.usecase.AbsenceClubLeaderUsecase
import kr.flooding.backend.domain.club.usecase.AbsenceClubMyselfUsecase
import kr.flooding.backend.domain.club.usecase.ApplyClubUsecase
import kr.flooding.backend.domain.club.usecase.AttendClubLeaderUsecase
import kr.flooding.backend.domain.club.usecase.AttendClubMyselfUsecase
import kr.flooding.backend.domain.club.usecase.CloseClubUsecase
import kr.flooding.backend.domain.club.usecase.ConfirmClubInviteUsecase
import kr.flooding.backend.domain.club.usecase.CreateClubUsecase
import kr.flooding.backend.domain.club.usecase.FetchClubFilterUsecase
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
	private val removeClubMemberUsecase: RemoveClubMemberUsecase,
	private val updateClubUsecase: UpdateClubUsecase,
	private val applyClubUsecase: ApplyClubUsecase,
	private val openClubUsecase: OpenClubUsecase,
	private val closeClubUsecase: CloseClubUsecase,
	private val removeClubUsecase: RemoveClubUsecase,
	private val withdrawClubUsecase: WithdrawClubUsecase,
	private val inviteClubMemberUsecase: InviteClubMemberUsecase,
	private val fetchClubUsecase: FetchClubUsecase,
	private val confirmClubInviteUsecase: ConfirmClubInviteUsecase,
	private val attendClubMyselfUsecase: AttendClubMyselfUsecase,
	private val absenceClubMyselfUsecase: AbsenceClubMyselfUsecase,
	private val attendClubLeaderUsecase: AttendClubLeaderUsecase,
	private val absenceClubLeaderUsecase: AbsenceClubLeaderUsecase,
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

	@PostMapping("{clubId}/open")
	fun openClub(
		@PathVariable clubId: UUID,
	): ResponseEntity<Unit> =
		openClubUsecase.execute(clubId).run {
			ResponseEntity.ok().build()
		}

	@PostMapping("{clubId}/close")
	fun closeClub(
		@PathVariable clubId: UUID,
	): ResponseEntity<Unit> =
		closeClubUsecase.execute(clubId).run {
			ResponseEntity.ok().build()
		}

	@DeleteMapping("{clubId}")
	fun removeClub(
		@PathVariable clubId: UUID,
	): ResponseEntity<Unit> =
		removeClubUsecase.execute(clubId).run {
			ResponseEntity.noContent().build()
		}

	@DeleteMapping("{clubId}/member")
	fun withdrawClub(
		@PathVariable clubId: UUID,
	): ResponseEntity<Unit> =
		withdrawClubUsecase.execute(clubId).run {
			ResponseEntity.noContent().build()
		}

	@PostMapping("{clubId}/member/{userId}")
	fun inviteClubMember(
		@PathVariable clubId: UUID,
		@PathVariable userId: UUID,
	): ResponseEntity<Unit> =
		inviteClubMemberUsecase.execute(clubId, userId).run {
			ResponseEntity.ok().build()
		}

	@PostMapping("invite/confirm")
	fun confirmInviteClub(
		@RequestParam code: String,
	): ResponseEntity<Unit> =
		confirmClubInviteUsecase
			.execute(code)
			.run {
				ResponseEntity.ok().build()
			}

	@GetMapping("{clubId}")
	fun fetchClub(
		@PathVariable clubId: UUID,
	): ResponseEntity<FetchClubResponse> =
		fetchClubUsecase.execute(clubId).run {
			ResponseEntity.ok(this)
		}

	@PostMapping("attend")
	fun attendanceClub(
		@Valid @RequestBody attendClubMyselfRequest: AttendClubMyselfRequest,
	): ResponseEntity<Util> =
		attendClubMyselfUsecase.execute(attendClubMyselfRequest).run {
			ResponseEntity.ok().build()
		}

	@PostMapping("absence")
	fun absenceClub(
		@Valid @RequestBody absenceClubMyselfRequest: AbsenceClubMyselfRequest,
	): ResponseEntity<Util> =
		absenceClubMyselfUsecase.execute(absenceClubMyselfRequest).run {
			ResponseEntity.ok().build()
		}

	@PostMapping("leader/attend")
	fun attendanceClubLeader(
		@Valid @RequestBody attendClubLeaderRequest: AttendClubLeaderRequest,
	): ResponseEntity<Util> =
		attendClubLeaderUsecase.execute(attendClubLeaderRequest).run {
			ResponseEntity.ok().build()
		}

	@PostMapping("leader/absence")
	fun absenceClubLeader(
		@Valid @RequestBody absenceClubLeaderRequest: AbsenceClubLeaderRequest,
	): ResponseEntity<Util> =
		absenceClubLeaderUsecase.execute(absenceClubLeaderRequest).run {
			ResponseEntity.ok().build()
		}
}
