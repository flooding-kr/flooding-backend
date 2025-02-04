package team.gsm.flooding.domain.attendance.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import team.gsm.flooding.domain.attendance.dto.request.ReserveHomebaseTableRequest
import team.gsm.flooding.domain.attendance.usecase.ReserveHomebaseTableUsecase

@RestController
@RequestMapping("attendance")
class AttendanceController (
	private val reserveHomebaseTableUsecase: ReserveHomebaseTableUsecase,
) {
	@PostMapping("homebase")
	fun reserveHomebaseTable(@RequestBody request: ReserveHomebaseTableRequest): ResponseEntity<Unit> {
		return reserveHomebaseTableUsecase.execute(request).run {
			ResponseEntity.ok().build()
		}
	}
}