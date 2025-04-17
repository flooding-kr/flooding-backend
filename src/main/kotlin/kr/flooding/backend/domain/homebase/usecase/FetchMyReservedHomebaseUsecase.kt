package kr.flooding.backend.domain.homebase.usecase

import kr.flooding.backend.domain.homebase.dto.common.response.FetchMyReservedHomebaseResponse
import kr.flooding.backend.domain.homebase.dto.web.response.FetchMyReservedHomebaseListResponse
import kr.flooding.backend.domain.homebase.persistence.repository.jdsl.HomebaseGroupJdslRepository
import kr.flooding.backend.global.util.UserUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
@Transactional
class FetchMyReservedHomebaseUsecase(
	private val homebaseGroupJdslRepository: HomebaseGroupJdslRepository,
	private val userUtil: UserUtil,
) {
	fun execute(): FetchMyReservedHomebaseListResponse {
		val currentUser = userUtil.getUser()
		return FetchMyReservedHomebaseListResponse(
			homebaseGroupJdslRepository
				.findWithHomebaseTableWithHomebaseAndProposerByProposerOrParticipantsAndAttendedAt(
					currentUser,
					LocalDate.now(),
				).map {
					FetchMyReservedHomebaseResponse.toDto(it, currentUser)
				},
		)
	}
}
