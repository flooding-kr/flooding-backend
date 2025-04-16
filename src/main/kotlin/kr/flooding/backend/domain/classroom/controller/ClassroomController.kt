package kr.flooding.backend.domain.classroom.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import kr.flooding.backend.domain.classroom.dto.request.FetchClassroomRequest
import kr.flooding.backend.domain.classroom.dto.response.FetchClassroomResponse
import kr.flooding.backend.domain.classroom.persistence.entity.BuildingType
import kr.flooding.backend.domain.classroom.usecase.FetchClassroomUsecase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Classroom", description = "교실")
@RestController
@RequestMapping("classroom")
class ClassroomController(
	private val fetchClassroomUsecase: FetchClassroomUsecase,
) {
	@Operation(summary = "교실 조회")
	@GetMapping
	fun fetchClassroom(
		@RequestParam floor: Int,
		@RequestParam buildingType: BuildingType,
		@RequestParam(defaultValue = "") search: String,
	): ResponseEntity<FetchClassroomResponse> =
		fetchClassroomUsecase
			.execute(
				FetchClassroomRequest(
					floor = floor,
					buildingType = buildingType,
					search = search,
				),
			).run {
				ResponseEntity.ok(this)
			}
}
