package kr.flooding.backend.domain.homebase.usecase

import kr.flooding.backend.domain.homebase.dto.response.FetchMyReservedHomebaseResponse
import kr.flooding.backend.domain.homebase.repository.jdsl.HomebaseGroupJdslRepository
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
	fun execute(): List<FetchMyReservedHomebaseResponse> {
		val currentUser = userUtil.getUser()
		return homebaseGroupJdslRepository
			.findWithHomebaseTableWithHomebaseAndProposerByProposerOrParticipantsAndAttendedAt(
				currentUser,
				LocalDate.now(),
			).map {
				FetchMyReservedHomebaseResponse.toDto(it, currentUser)
			}
	}
}
