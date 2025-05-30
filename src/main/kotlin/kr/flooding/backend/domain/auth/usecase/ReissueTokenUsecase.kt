package kr.flooding.backend.domain.auth.usecase

import kr.flooding.backend.domain.auth.dto.response.ReissueTokenResponse
import kr.flooding.backend.domain.auth.persistence.entity.RefreshToken
import kr.flooding.backend.domain.auth.persistence.repository.RefreshTokenRepository
import kr.flooding.backend.global.exception.ExceptionEnum
import kr.flooding.backend.global.exception.HttpException
import kr.flooding.backend.global.exception.toPair
import kr.flooding.backend.global.properties.JwtProperties
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
	private val jwtProperties: JwtProperties,
) {
	fun execute(resolveRefreshToken: String): ReissueTokenResponse {
		val currentUserId = UUID.fromString(jwtProvider.getIdByRefreshToken(resolveRefreshToken))

		val savedRefreshToken =
			refreshTokenRepository.findById(currentUserId).orElseThrow {
				HttpException(ExceptionEnum.AUTH.NOT_FOUND_REFRESH_TOKEN.toPair())
			}

		if (resolveRefreshToken != savedRefreshToken.refreshToken) {
			throw HttpException(ExceptionEnum.AUTH.INVALID_REFRESH_TOKEN.toPair())
		}

		val newAccessToken = jwtProvider.generateToken(currentUserId, JwtType.ACCESS_TOKEN)
		val newRefreshToken = deleteRefreshTokenOrSave(currentUserId)

		return ReissueTokenResponse(
			accessToken = newAccessToken.token,
			accessTokenExpiredAt = newAccessToken.expiredAt,
			refreshToken = newRefreshToken.token,
			refreshTokenExpiredAt = newRefreshToken.expiredAt,
		)
	}

	private fun deleteRefreshTokenOrSave(id: UUID): JwtDetails {
		val refreshToken =
			refreshTokenRepository.findById(id).orElseThrow {
				HttpException(ExceptionEnum.AUTH.NOT_FOUND_REFRESH_TOKEN.toPair())
			}

		refreshTokenRepository.delete(refreshToken)
		val newRefreshToken = jwtProvider.generateToken(id, JwtType.REFRESH_TOKEN)

		val newRefreshTokenEntity =
			RefreshToken(
				id = id,
				refreshToken = newRefreshToken.token,
				expires = jwtProperties.refreshTokenExpires,
			)

		refreshTokenRepository.save(newRefreshTokenEntity)

		return newRefreshToken
	}
}
