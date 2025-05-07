package kr.flooding.backend.domain.music.usecase

import kr.flooding.backend.domain.music.enums.MusicOrderType
import kr.flooding.backend.domain.music.dto.web.response.FetchMusicResponse
import kr.flooding.backend.domain.music.dto.web.response.MusicResponse
import kr.flooding.backend.domain.music.persistence.repository.jdsl.MusicJdslRepository
import kr.flooding.backend.domain.musicLike.persistence.repository.jpa.MusicLikeJpaRepository
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
		val musicList = musicJdslRepository.findAllByCreatedDateOrderByMusicOrderTypeAndUserContainsMusicLike(
			createdDate = date,
			musicOrderType = orderType,
			user = currentUser
		)

		return FetchMusicResponse(
			musicList = musicList.map {
				MusicResponse.toDto(
					music = it.music,
					isAppliedByUser = it.music.proposer == currentUser,
					isLikedByUser = it.isLikedByUser
				)
			}
		)
	}
}
