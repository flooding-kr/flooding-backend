package kr.flooding.backend.domain.homebase.usecase

import kr.flooding.backend.domain.homebase.dto.request.FetchReservedHomebaseTableRequest
import kr.flooding.backend.domain.homebase.dto.response.FetchReservedHomebaseResponse
import kr.flooding.backend.domain.homebase.repository.jdsl.HomebaseGroupJdslRepository
import kr.flooding.backend.domain.homebaseTable.repository.jdsl.HomebaseTableJdslRepository
import kr.flooding.backend.global.util.UserUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
@Transactional
class FetchReservedHomebaseTableUsecase(
	private val homebaseGroupJdslRepository: HomebaseGroupJdslRepository,
	private val homebaseTableJdslRepository: HomebaseTableJdslRepository,
	private val userUtil: UserUtil,
) {
	fun execute(request: FetchReservedHomebaseTableRequest): List<FetchReservedHomebaseResponse> {
		val homebaseGroupList =
			homebaseGroupJdslRepository.findWithParticipantsAndProposerByPeriodAndHomebaseTableHomebaseFloorAndAttendedAt(
				request.period,
				request.floor,
				LocalDate.now(),
			)
		val homebaseTableList = homebaseTableJdslRepository.findWithHomebaseByFloor(request.floor)
		val currentUser = userUtil.getUser()

		return homebaseTableList.map { homebaseTable ->
			val currentHomebaseGroup = homebaseGroupList.find { homebaseTable == it.homebaseTable }
			FetchReservedHomebaseResponse.toDto(homebaseTable, currentHomebaseGroup, currentUser)
		}
	}
}
