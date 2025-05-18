package kr.flooding.backend.domain.music.usecase.admin

import kr.flooding.backend.domain.music.persistence.repository.jpa.MusicJpaRepository
import kr.flooding.backend.global.exception.ExceptionEnum
import kr.flooding.backend.global.exception.HttpException
import kr.flooding.backend.global.exception.toPair
import kr.flooding.backend.global.util.DateUtil.Companion.atEndOfDay
import kr.flooding.backend.global.util.UserUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.util.UUID

@Service
@Transactional
class RemoveMusicUsecase(
	private val musicJpaRepository: MusicJpaRepository,
) {
	fun execute(musicId: UUID) {
		val music = musicJpaRepository.findById(musicId).orElseThrow {
			HttpException(ExceptionEnum.MUSIC.NOT_FOUND_REQUESTED_MUSIC.toPair())
		}
		musicJpaRepository.delete(music)
	}
}
