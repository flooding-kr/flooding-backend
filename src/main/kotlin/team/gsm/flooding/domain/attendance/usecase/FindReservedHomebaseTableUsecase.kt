package team.gsm.flooding.domain.attendance.usecase

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import team.gsm.flooding.domain.attendance.dto.request.FindReservedHomebaseTableRequest
import team.gsm.flooding.domain.attendance.dto.response.HomebaseGroupResponse
import team.gsm.flooding.domain.attendance.repository.HomebaseGroupRepository

@Service
@Transactional
class FindReservedHomebaseTableUsecase(
    private val homebaseGroupRepository: HomebaseGroupRepository
) {
    fun execute(request: FindReservedHomebaseTableRequest): List<HomebaseGroupResponse> {
        return homebaseGroupRepository.findByPeriodAndHomebaseTableHomebaseFloor(request.period, request.floor)
            .map { HomebaseGroupResponse.toDto(it) }
    }
}