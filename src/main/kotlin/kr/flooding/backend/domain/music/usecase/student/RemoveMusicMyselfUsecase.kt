package kr.flooding.backend.domain.music.usecase.student

import kr.flooding.backend.domain.music.persistence.repository.jpa.MusicJpaRepository
import kr.flooding.backend.global.exception.ExceptionEnum
import kr.flooding.backend.global.exception.HttpException
import kr.flooding.backend.global.exception.toPair
import kr.flooding.backend.global.util.DateUtil.Companion.atEndOfDay
import kr.flooding.backend.global.util.UserUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
@Transactional
class RemoveMusicMyselfUsecase(
	private val userUtil: UserUtil,
	private val musicJpaRepository: MusicJpaRepository,
) {
	fun execute() {
		val currentUser = userUtil.getUser()
		val currentDate = LocalDate.now()
		val music =
			musicJpaRepository.findByProposerAndCreatedAtBetween(
				currentUser,
				currentDate.atStartOfDay(),
				currentDate.atEndOfDay()
			).orElseThrow { HttpException(ExceptionEnum.MUSIC.NOT_FOUND_REQUESTED_MUSIC.toPair()) }

		musicJpaRepository.delete(music)
	}
}
