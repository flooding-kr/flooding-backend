package team.gsm.flooding.domain.attendance.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import team.gsm.flooding.domain.attendance.dto.request.ReserveHomebaseTableRequest
import team.gsm.flooding.domain.attendance.dto.request.UpdateHomebaseTableRequest
import team.gsm.flooding.domain.attendance.usecase.CancelHomebaseTableUsecase
import team.gsm.flooding.domain.attendance.usecase.ReserveHomebaseTableUsecase
import team.gsm.flooding.domain.attendance.usecase.UpdateHomebaseTableUsecase

@RestController
@RequestMapping("attendance")
class AttendanceController (
	private val reserveHomebaseTableUsecase: ReserveHomebaseTableUsecase,
	private val updateHomebaseTableUsecase: UpdateHomebaseTableUsecase,
	private val cancelHomebaseTableUsecase: CancelHomebaseTableUsecase
) {
	@PostMapping("homebase")
	fun reserveHomebaseTable(@RequestBody request: ReserveHomebaseTableRequest): ResponseEntity<Unit> {
		return reserveHomebaseTableUsecase.execute(request).run {
			ResponseEntity.ok().build()
		}
	}

	@PatchMapping("homebase")
	fun updateHomebaseTable(@RequestBody request: UpdateHomebaseTableRequest): ResponseEntity<Unit> {
		return updateHomebaseTableUsecase.execute(request).run {
			ResponseEntity.ok().build()
		}
	}

	@DeleteMapping("homebase")
	fun cancelHomebaseTable(): ResponseEntity<Unit> {
		return cancelHomebaseTableUsecase.execute().run {
			ResponseEntity.ok().build()
		}
	}
}