package kr.flooding.backend.domain.homebase.usecase

import kr.flooding.backend.domain.classroom.repository.HomebaseTableRepository
import kr.flooding.backend.domain.homebase.dto.request.FetchReservedHomebaseTableRequest
import kr.flooding.backend.domain.homebase.dto.response.FetchReservedHomebaseResponse
import kr.flooding.backend.domain.homebase.repository.HomebaseGroupRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
@Transactional
class FetchReservedHomebaseTableUsecase(
	private val homebaseGroupRepository: HomebaseGroupRepository,
	private val homebaseTableRepository: HomebaseTableRepository,
) {
	fun execute(request: FetchReservedHomebaseTableRequest): List<FetchReservedHomebaseResponse> {
		val homebaseGroupList =
			homebaseGroupRepository.findByPeriodAndHomebaseTableHomebaseFloorAndAttendedAt(
				request.period,
				request.floor,
				LocalDate.now(),
			)
		val homebaseTableList = homebaseTableRepository.findByHomebaseFloor(request.floor)

		return homebaseTableList.map { homebaseTable ->
			val currentHomebaseGroup = homebaseGroupList.find { homebaseTable == it.homebaseTable }
			FetchReservedHomebaseResponse.toDto(homebaseTable, currentHomebaseGroup)
		}
	}
}
