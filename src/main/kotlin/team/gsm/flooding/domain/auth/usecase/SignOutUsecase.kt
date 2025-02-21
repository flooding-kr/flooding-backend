package team.gsm.flooding.domain.auth.usecase

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import team.gsm.flooding.domain.auth.repository.RefreshTokenRepository
import team.gsm.flooding.global.exception.ExceptionEnum
import team.gsm.flooding.global.exception.HttpException
import team.gsm.flooding.global.security.jwt.JwtProvider

@Service
@Transactional
class SignOutUsecase(
	private val refreshTokenRepository: RefreshTokenRepository,
	private val jwtProvider: JwtProvider,
) {
	fun execute(resolveRefreshToken: String) {
		val savedRefreshToken = jwtProvider.getSavedRefreshTokenByRefreshToken(resolveRefreshToken)

		if (resolveRefreshToken != savedRefreshToken.refreshToken) {
			throw HttpException(ExceptionEnum.INVALID_REFRESH_TOKEN)
		}

		refreshTokenRepository.delete(savedRefreshToken)
	}
}
