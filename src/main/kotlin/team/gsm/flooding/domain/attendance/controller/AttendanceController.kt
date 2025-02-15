package team.gsm.flooding.domain.attendance.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import team.gsm.flooding.domain.attendance.dto.request.FindReservedHomebaseTableRequest
import team.gsm.flooding.domain.attendance.dto.request.ReserveHomebaseTableRequest
import team.gsm.flooding.domain.attendance.dto.response.FindMyReservedHomebaseResponse
import team.gsm.flooding.domain.attendance.dto.response.FindReservedHomebaseResponse
import team.gsm.flooding.domain.attendance.usecase.CancelHomebaseTableUsecase
import team.gsm.flooding.domain.attendance.usecase.FindMyReservedHomebaseUsecase
import team.gsm.flooding.domain.attendance.usecase.FindReservedHomebaseTableUsecase
import team.gsm.flooding.domain.attendance.usecase.ReserveHomebaseTableUsecase
import java.util.UUID

@RestController
@RequestMapping("attendance")
class AttendanceController(
	private val reserveHomebaseTableUsecase: ReserveHomebaseTableUsecase,
	private val findMyReservedHomebaseTableUsecase: FindMyReservedHomebaseUsecase,
	private val findReservedHomebaseTableUsecase: FindReservedHomebaseTableUsecase,
	private val cancelHomebaseTableUsecase: CancelHomebaseTableUsecase,
) {
	@PostMapping("homebase")
	fun reserveHomebaseTable(
		@RequestBody request: ReserveHomebaseTableRequest,
	): ResponseEntity<Unit> =
		reserveHomebaseTableUsecase.execute(request).run {
			ResponseEntity.ok().build()
		}

	@GetMapping("homebase")
	fun findReservedHomebaseTable(
		@RequestBody request: FindReservedHomebaseTableRequest,
	): ResponseEntity<List<FindReservedHomebaseResponse>> =
		findReservedHomebaseTableUsecase.execute(request).run {
			ResponseEntity.ok(this)
		}

	@GetMapping("homebase/myself")
	fun findMyReservedHomebaseTable(): ResponseEntity<List<FindMyReservedHomebaseResponse>> =
		findMyReservedHomebaseTableUsecase.execute().run {
			ResponseEntity.ok(this)
		}

	@DeleteMapping("homebase/{homebaseGroupId}")
	fun cancelReserveHomebaseTable(
		@PathVariable homebaseGroupId: UUID,
	): ResponseEntity<Unit> =
		cancelHomebaseTableUsecase.execute(homebaseGroupId).run {
			ResponseEntity.ok().build()
		}
}
