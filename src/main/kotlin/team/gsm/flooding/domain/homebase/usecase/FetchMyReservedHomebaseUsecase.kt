package team.gsm.flooding.domain.homebase.usecase

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import team.gsm.flooding.domain.homebase.dto.response.FetchMyReservedHomebaseResponse
import team.gsm.flooding.domain.homebase.repository.HomebaseGroupRepository
import team.gsm.flooding.global.util.UserUtil
import java.time.LocalDate

@Service
@Transactional
class FetchMyReservedHomebaseUsecase(
	private val homebaseGroupRepository: HomebaseGroupRepository,
	private val userUtil: UserUtil,
) {
	fun execute(): List<FetchMyReservedHomebaseResponse> =
		homebaseGroupRepository
			.findByProposerStudentAndAttendedAt(userUtil.getUser(), LocalDate.now())
			.map { FetchMyReservedHomebaseResponse.toDto(it, userUtil.getUser()) }
}
