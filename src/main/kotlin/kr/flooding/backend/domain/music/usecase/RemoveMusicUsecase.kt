package kr.flooding.backend.domain.music.usecase

import kr.flooding.backend.domain.music.persistence.repository.jpa.MusicJpaRepository
import kr.flooding.backend.global.exception.ExceptionEnum
import kr.flooding.backend.global.exception.HttpException
import kr.flooding.backend.global.exception.toPair
import kr.flooding.backend.global.util.DateUtil
import kr.flooding.backend.global.util.UserUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class RemoveMusicUsecase(
	private val userUtil: UserUtil,
	private val musicJpaRepository: MusicJpaRepository,
) {
	fun execute() {
		val currentUser = userUtil.getUser()
		val music =
			musicJpaRepository.findByProposerAndCreatedAtBetween(
				currentUser,
				DateUtil.getAtStartOfToday(),
				DateUtil.getAtEndOfToday()
			).orElseThrow { HttpException(ExceptionEnum.MUSIC.NOT_FOUND_REQUESTED_MUSIC.toPair()) }

		musicJpaRepository.delete(music)
	}
}
