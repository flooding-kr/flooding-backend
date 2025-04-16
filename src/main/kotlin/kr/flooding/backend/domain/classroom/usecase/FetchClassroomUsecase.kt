package kr.flooding.backend.domain.classroom.usecase

import kr.flooding.backend.domain.classroom.dto.request.FetchClassroomRequest
import kr.flooding.backend.domain.classroom.dto.response.FetchClassroomResponse
import kr.flooding.backend.domain.classroom.persistence.repository.jdsl.ClassroomJdslRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class FetchClassroomUsecase(
	private val classroomJdslRepository: ClassroomJdslRepository,
) {
	fun execute(fetchClassroomRequest: FetchClassroomRequest): FetchClassroomResponse =
		FetchClassroomResponse.toDto(
			classroomJdslRepository.findWithTeacherByFloorAndBuildingTypeAndInName(
				floor = fetchClassroomRequest.floor,
				buildingType = fetchClassroomRequest.buildingType,
				name = fetchClassroomRequest.search,
			),
		)
}
