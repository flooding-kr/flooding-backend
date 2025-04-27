package kr.flooding.backend.domain.attendance.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import kr.flooding.backend.domain.attendance.dto.web.request.*
import kr.flooding.backend.domain.attendance.dto.web.response.FetchAttendanceResponse
import kr.flooding.backend.domain.attendance.dto.web.response.FetchClubAttendanceListResponse
import kr.flooding.backend.domain.attendance.enums.AttendanceStatus
import kr.flooding.backend.domain.attendance.usecase.*
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDate
import java.util.UUID

@Tag(name = "Attendance", description = "출석")
@RestController
@RequestMapping("attendance")
class AttendanceController(
	private val attendClubMyselfUsecase: AttendClubMyselfUsecase,
	private val absenceClubMyselfUsecase: AbsenceClubMyselfUsecase,
	private val attendClubLeaderUsecase: AttendClubLeaderUsecase,
	private val absenceClubLeaderUsecase: AbsenceClubLeaderUsecase,
	private val fetchMyselfAttendanceUsecase: FetchMyselfAttendanceUsecase,
	private val fetchClubAttendanceUsecase: FetchClubAttendanceUsecase,
) {
	@Operation(summary = "동아리 출석")
	@PostMapping("club")
	fun attendanceClub(
		@Valid @RequestBody attendClubMyselfRequest: AttendClubMyselfRequest,
	): ResponseEntity<Unit> =
		attendClubMyselfUsecase.execute(attendClubMyselfRequest).run {
			ResponseEntity.ok().build()
		}

	@Operation(summary = "동아리 미출석")
	@DeleteMapping("club")
	fun absenceClub(
		@Valid @RequestBody absenceClubMyselfRequest: AbsenceClubMyselfRequest,
	): ResponseEntity<Unit> =
		absenceClubMyselfUsecase.execute(absenceClubMyselfRequest).run {
			ResponseEntity.ok().build()
		}

	@Operation(summary = "동아리 일괄 출석")
	@PostMapping("club/leader")
	fun attendanceClubLeader(
		@Valid @RequestBody attendClubLeaderRequest: AttendClubLeaderRequest,
	): ResponseEntity<Unit> =
		attendClubLeaderUsecase.execute(attendClubLeaderRequest).run {
			ResponseEntity.ok().build()
		}

	@Operation(summary = "동아리 일괄 미출석")
	@DeleteMapping("club/leader")
	fun absenceClubLeader(
		@Valid @RequestBody absenceClubLeaderRequest: AbsenceClubLeaderRequest,
	): ResponseEntity<Unit> =
		absenceClubLeaderUsecase.execute(absenceClubLeaderRequest).run {
			ResponseEntity.ok().build()
		}

	@Operation(summary = "나의 출석 조회")
	@GetMapping("myself")
	fun fetchMyselfAttendance(
		@RequestParam date: LocalDate,
		@RequestParam period: Int,
	): ResponseEntity<FetchAttendanceResponse> =
		fetchMyselfAttendanceUsecase.execute(date, period).let {
			ResponseEntity.ok(it)
		}

	@Operation(summary = "동아리 출석 조회")
	@GetMapping("/club/{clubId}")
	fun fetchAttendance(
		@RequestParam date: LocalDate,
		@RequestParam period: Int,
		@RequestParam status: AttendanceStatus?,
		@PathVariable clubId: UUID,
	): ResponseEntity<FetchClubAttendanceListResponse> {
		return fetchClubAttendanceUsecase.execute(
			FetchClubAttendanceRequest(
				date = date,
				period = period,
				clubId = clubId,
				status = status
			)
		).let {
			ResponseEntity.ok(it)
		}
	}
}
