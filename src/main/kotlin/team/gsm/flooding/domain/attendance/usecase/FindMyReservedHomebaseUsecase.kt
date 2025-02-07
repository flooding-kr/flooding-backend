package team.gsm.flooding.domain.attendance.usecase

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import team.gsm.flooding.domain.attendance.dto.response.HomebaseGroupResponse
import team.gsm.flooding.domain.attendance.repository.HomebaseGroupRepository
import team.gsm.flooding.global.util.UserUtil
import java.time.LocalDate

@Service
@Transactional
class FindMyReservedHomebaseUsecase(
    private val homebaseGroupRepository: HomebaseGroupRepository,
    private val userUtil: UserUtil,
) {
    fun execute(): List<HomebaseGroupResponse> {
        return homebaseGroupRepository.findByProposerStudentAndAttendedAt(userUtil.getUser(), LocalDate.now())
            .map { HomebaseGroupResponse.toDto(it) }
    }
}