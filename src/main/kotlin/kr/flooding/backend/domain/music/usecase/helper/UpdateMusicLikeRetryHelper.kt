package kr.flooding.backend.domain.music.usecase.helper

import kr.flooding.backend.domain.music.dto.response.UpdateMusicLikeResponse
import kr.flooding.backend.domain.music.persistence.repository.jpa.MusicJpaRepository
import kr.flooding.backend.domain.musicLike.persistence.entity.MusicLike
import kr.flooding.backend.domain.musicLike.persistence.repository.jpa.MusicLikeJpaRepository
import kr.flooding.backend.domain.user.entity.User
import kr.flooding.backend.global.exception.ExceptionEnum
import kr.flooding.backend.global.exception.HttpException
import kr.flooding.backend.global.exception.toPair
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.orm.ObjectOptimisticLockingFailureException
import org.springframework.retry.annotation.Backoff
import org.springframework.retry.annotation.Recover
import org.springframework.retry.annotation.Retryable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import java.util.UUID
import kotlin.jvm.optionals.getOrNull

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
class UpdateMusicLikeRetryHelper(
	private val musicJpaRepository: MusicJpaRepository,
	private val musicLikeJpaRepository: MusicLikeJpaRepository,
) {
	@Retryable(
		retryFor = [ObjectOptimisticLockingFailureException::class],
		maxAttempts = 3,
		backoff =
			Backoff(
				delay = 30,
				maxDelay = 150,
				multiplier = 2.0,
				random = true,
			),
		notRecoverable = [HttpException::class],
	)
	fun execute(
		musicId: UUID,
		currentUser: User,
	): UpdateMusicLikeResponse {
		try {
			val music =
				musicJpaRepository.findById(musicId)
					.orElseThrow { HttpException(ExceptionEnum.MUSIC.NOT_FOUND_MUSIC.toPair()) }
			val musicLike = musicLikeJpaRepository.findByMusicAndUser(music, currentUser).getOrNull()

			musicLike?.let {
				music.decreaseLikeCount()
				musicLikeJpaRepository.deleteById(requireNotNull(it.id))
			} ?: run {
				music.incrementLikeCount()
				musicLikeJpaRepository.save(
					MusicLike(
						music = music,
						user = currentUser,
					),
				)
			}

			return UpdateMusicLikeResponse(
				likeCount = music.likeCount,
				hasUserLiked = musicLike == null,
			)
		} catch (e: DataIntegrityViolationException) {
			throw HttpException(ExceptionEnum.MUSIC.ALREADY_MUSIC_LIKE.toPair())
		} catch (e: EmptyResultDataAccessException) {
			throw HttpException(ExceptionEnum.MUSIC.ALREADY_MUSIC_LIKE.toPair())
		}
	}

	@Recover
	fun recover(
		e: ObjectOptimisticLockingFailureException,
		musicId: UUID,
		currentUser: User,
	): UpdateMusicLikeResponse {
		throw HttpException(ExceptionEnum.MUSIC.TOO_MANY_MUSIC_REQUESTS.toPair())
	}
}
