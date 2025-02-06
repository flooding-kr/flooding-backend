package team.gsm.flooding.domain.attendance.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import team.gsm.flooding.domain.attendance.dto.request.ReserveHomebaseTableRequest
import team.gsm.flooding.domain.attendance.usecase.CancelHomebaseTableUsecase
import team.gsm.flooding.domain.attendance.dto.response.FindReservedHomebaseTableResponse
import team.gsm.flooding.domain.attendance.usecase.FindReservedHomebaseUsecase
import team.gsm.flooding.domain.attendance.usecase.ReserveHomebaseTableUsecase
import java.util.UUID

@RestController
@RequestMapping("attendance")
class AttendanceController (
	private val reserveHomebaseTableUsecase: ReserveHomebaseTableUsecase,
	private val findReservedHomebaseTableUsecase: FindReservedHomebaseUsecase,
	private val cancelHomebaseTableUsecase: CancelHomebaseTableUsecase,
) {
	@PostMapping("homebase")
	fun reserveHomebaseTable(@RequestBody request: ReserveHomebaseTableRequest): ResponseEntity<Unit> {
		return reserveHomebaseTableUsecase.execute(request).run {
			ResponseEntity.ok().build()
		}
	}

	@GetMapping("homebase")
	fun findReservedHomebaseTable(): ResponseEntity<List<FindReservedHomebaseTableResponse>> {
		return findReservedHomebaseTableUsecase.execute().run {
			ResponseEntity.ok(this)
		}
	}

	@DeleteMapping("homebase/{homebaseGroupId}")
	fun cancelReserveHomebaseTable(@PathVariable homebaseGroupId: UUID): ResponseEntity<Unit> {
		return cancelHomebaseTableUsecase.execute(homebaseGroupId).run {
			ResponseEntity.ok().build()
		}
	}
}