package team.gsm.flooding.domain.auth.usecase

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import team.gsm.flooding.domain.auth.dto.response.ReissueTokenResponse
import team.gsm.flooding.domain.auth.entity.RefreshToken
import team.gsm.flooding.domain.auth.repository.RefreshTokenRepository
import team.gsm.flooding.global.exception.ExceptionEnum
import team.gsm.flooding.global.exception.ExpectedException
import team.gsm.flooding.global.security.jwt.JwtProvider
import team.gsm.flooding.global.security.jwt.JwtType
import team.gsm.flooding.global.security.jwt.dto.JwtDetails
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
			throw ExpectedException(ExceptionEnum.INVALID_REFRESH_TOKEN)
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
				ExpectedException(ExceptionEnum.NOT_FOUND_REFRESH_TOKEN)
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
