package kr.flooding.backend.domain.attendance.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import kr.flooding.backend.domain.attendance.dto.request.AbsenceClubLeaderRequest
import kr.flooding.backend.domain.attendance.dto.request.AbsenceClubMyselfRequest
import kr.flooding.backend.domain.attendance.dto.request.AttendClubLeaderRequest
import kr.flooding.backend.domain.attendance.dto.request.AttendClubMyselfRequest
import kr.flooding.backend.domain.attendance.dto.response.FetchAttendanceResponse
import kr.flooding.backend.domain.attendance.usecase.AbsenceClubLeaderUsecase
import kr.flooding.backend.domain.attendance.usecase.AbsenceClubMyselfUsecase
import kr.flooding.backend.domain.attendance.usecase.AttendClubLeaderUsecase
import kr.flooding.backend.domain.attendance.usecase.AttendClubMyselfUsecase
import kr.flooding.backend.domain.attendance.usecase.FetchAttendanceUsecase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate

@Tag(name = "Attendance", description = "출석")
@RestController
@RequestMapping("attendance")
class AttendanceController(
	private val attendClubMyselfUsecase: AttendClubMyselfUsecase,
	private val absenceClubMyselfUsecase: AbsenceClubMyselfUsecase,
	private val attendClubLeaderUsecase: AttendClubLeaderUsecase,
	private val absenceClubLeaderUsecase: AbsenceClubLeaderUsecase,
	private val fetchAttendanceUsecase: FetchAttendanceUsecase,
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
	fun fetchAttendanceMyself(
		@RequestParam date: LocalDate,
		@RequestParam period: Int,
	): ResponseEntity<FetchAttendanceResponse> =
		fetchAttendanceUsecase.execute(date, period).let {
			ResponseEntity.ok(it)
		}
}
