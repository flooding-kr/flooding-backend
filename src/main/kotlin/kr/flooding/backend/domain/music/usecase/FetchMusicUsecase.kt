package kr.flooding.backend.domain.music.usecase

import kr.flooding.backend.domain.music.dto.request.MusicOrderType
import kr.flooding.backend.domain.music.dto.response.FetchMusicResponse
import kr.flooding.backend.domain.music.dto.response.MusicResponse
import kr.flooding.backend.domain.music.persistence.repository.jdsl.MusicJdslRepository
import kr.flooding.backend.global.util.UserUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
@Transactional(readOnly = true)
class FetchMusicUsecase(
	private val musicJdslRepository: MusicJdslRepository,
	private val userUtil: UserUtil
) {
	fun execute(
		date: LocalDate,
		orderType: MusicOrderType,
	): FetchMusicResponse {
		val currentUser = userUtil.getUser()
		val musicList = musicJdslRepository.findAllByCreatedDateOrderByMusicOrderType(date, orderType)

		return FetchMusicResponse(
			musicList = musicList.map {
				MusicResponse.toDto(
					music = it,
					currentUser = currentUser,
				)
			}
		)
	}
}
