package team.gsm.flooding.domain.attendance.usecase

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import team.gsm.flooding.domain.attendance.dto.response.FindReservedHomebaseTableResponse
import team.gsm.flooding.domain.attendance.repository.HomebaseGroupRepository
import team.gsm.flooding.global.util.UserUtil

@Service
@Transactional
class FindReservedHomebaseUsecase(
    private val homebaseGroupRepository: HomebaseGroupRepository,
    private val userUtil: UserUtil,
) {
    fun execute(): List<FindReservedHomebaseTableResponse> {
        return homebaseGroupRepository.findByProposerStudent(userUtil.getUser()).map { FindReservedHomebaseTableResponse.toDto(it) }
    }
}