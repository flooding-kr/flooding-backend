package kr.flooding.backend.domain.auth.usecase

import kr.flooding.backend.domain.auth.repository.RefreshTokenRepository
import kr.flooding.backend.global.exception.ExceptionEnum
import kr.flooding.backend.global.exception.HttpException
import kr.flooding.backend.global.exception.toPair
import kr.flooding.backend.global.security.jwt.JwtProvider
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class SignOutUsecase(
	private val refreshTokenRepository: RefreshTokenRepository,
	private val jwtProvider: JwtProvider,
) {
	fun execute(resolveRefreshToken: String) {
		val savedRefreshToken = jwtProvider.getSavedRefreshTokenByRefreshToken(resolveRefreshToken)

		if (resolveRefreshToken != savedRefreshToken.refreshToken) {
			throw HttpException(ExceptionEnum.AUTH.INVALID_REFRESH_TOKEN.toPair())
		}

		refreshTokenRepository.delete(savedRefreshToken)
	}
}
