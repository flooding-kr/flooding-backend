package kr.flooding.backend.domain.music.usecase

import kr.flooding.backend.domain.music.dto.web.request.CreateMusicRequest
import kr.flooding.backend.domain.music.persistence.entity.Music
import kr.flooding.backend.domain.music.persistence.repository.jpa.MusicJpaRepository
import kr.flooding.backend.global.exception.ExceptionEnum
import kr.flooding.backend.global.exception.HttpException
import kr.flooding.backend.global.exception.toPair
import kr.flooding.backend.global.thirdparty.youtube.YoutubeAdapter
import kr.flooding.backend.global.util.DateUtil
import kr.flooding.backend.global.util.UserUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class CreateMusicUsecase(
	private val youtubeAdapter: YoutubeAdapter,
	private val userUtil: UserUtil,
	private val musicJpaRepository: MusicJpaRepository,
) {
	fun execute(createMusicRequest: CreateMusicRequest) {
		val currentUser = userUtil.getUser()

		if(musicJpaRepository.existsByProposerAndCreatedAtBetween(
				currentUser,
				DateUtil.getAtStartOfToday(),
				DateUtil.getAtEndOfToday()
		)) {
			throw HttpException(ExceptionEnum.MUSIC.ALREADY_REQUESTED_MUSIC.toPair())
		}

		val youtubeInfo = youtubeAdapter.fetchYoutubeInfo(createMusicRequest.musicUrl)
		musicJpaRepository.save(
			Music(
				musicUrl = youtubeInfo.musicUrl,
				title = youtubeInfo.title,
				thumbImageUrl = youtubeInfo.thumbnailImageUrl,
				proposer = currentUser,
			),
		)
	}
}
