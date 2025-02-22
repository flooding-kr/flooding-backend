package kr.flooding.backend.domain.auth.usecase

import kr.flooding.backend.domain.auth.dto.response.ReissueTokenResponse
import kr.flooding.backend.domain.auth.entity.RefreshToken
import kr.flooding.backend.domain.auth.repository.RefreshTokenRepository
import kr.flooding.backend.global.exception.ExceptionEnum
import kr.flooding.backend.global.exception.HttpException
import kr.flooding.backend.global.security.jwt.JwtProvider
import kr.flooding.backend.global.security.jwt.JwtType
import kr.flooding.backend.global.security.jwt.dto.JwtDetails
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
@Transactional
class ReissueTokenUsecase(
	private val jwtProvider: JwtProvider,
	private val refreshTokenRepository: RefreshTokenRepository,
) {
	fun execute(resolveRefreshToken: String): ReissueTokenResponse {
		val currentUserId = jwtProvider.getIdByRefreshToken(resolveRefreshToken)
		val savedRefreshToken = jwtProvider.getSavedRefreshTokenByRefreshToken(resolveRefreshToken)

		if (resolveRefreshToken != savedRefreshToken.refreshToken) {
			throw HttpException(ExceptionEnum.INVALID_REFRESH_TOKEN)
		}

		val newAccessToken = jwtProvider.generateToken(currentUserId, JwtType.ACCESS_TOKEN)
		val newRefreshToken = deleteRefreshTokenOrSave(currentUserId)

		refreshTokenRepository.save(
			RefreshToken(
				id = UUID.fromString(currentUserId),
				refreshToken = newRefreshToken.token,
				expires = jwtProvider.refreshTokenExpires,
			),
		)

		return ReissueTokenResponse(
			accessToken = newAccessToken.token,
			accessTokenExpiredAt = newAccessToken.expiredAt,
			refreshToken = newRefreshToken.token,
			refreshTokenExpiredAt = newRefreshToken.expiredAt,
		)
	}

	fun deleteRefreshTokenOrSave(id: String): JwtDetails {
		val refreshToken =
			refreshTokenRepository.findById(UUID.fromString(id)).orElseThrow {
				HttpException(ExceptionEnum.NOT_FOUND_REFRESH_TOKEN)
			}

		refreshTokenRepository.delete(refreshToken)
		val newRefreshToken = jwtProvider.generateToken(id, JwtType.REFRESH_TOKEN)

		val newRefreshTokenEntity =
			RefreshToken(
				id = UUID.fromString(id),
				refreshToken = newRefreshToken.token,
				expires = jwtProvider.refreshTokenExpires,
			)

		refreshTokenRepository.save(newRefreshTokenEntity)

		return newRefreshToken
	}
}
