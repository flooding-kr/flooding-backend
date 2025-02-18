package team.gsm.flooding.domain.homebase.usecase

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import team.gsm.flooding.domain.classroom.repository.HomebaseTableRepository
import team.gsm.flooding.domain.homebase.dto.request.FindReservedHomebaseTableRequest
import team.gsm.flooding.domain.homebase.dto.response.FindReservedHomebaseResponse
import team.gsm.flooding.domain.homebase.repository.HomebaseGroupRepository
import java.time.LocalDate

@Service
@Transactional
class FindReservedHomebaseTableUsecase(
	private val homebaseGroupRepository: HomebaseGroupRepository,
	private val homebaseTableRepository: HomebaseTableRepository,
) {
	fun execute(request: FindReservedHomebaseTableRequest): List<FindReservedHomebaseResponse> {
		val homebaseGroupList =
			homebaseGroupRepository.findByPeriodAndHomebaseTableHomebaseFloorAndAttendedAt(
				request.period,
				request.floor,
				LocalDate.now(),
			)
		val homebaseTableList = homebaseTableRepository.findByHomebaseFloor(request.floor)

		return homebaseTableList.map { homebaseTable ->
			val currentHomebaseGroup = homebaseGroupList.find { homebaseTable == it.homebaseTable }
			FindReservedHomebaseResponse.toDto(homebaseTable, currentHomebaseGroup)
		}
	}
}
