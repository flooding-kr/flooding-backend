package com.example.demo.domain.auth.usecase

import com.example.demo.domain.auth.dto.request.SignInRequest
import com.example.demo.domain.auth.dto.response.ReissueTokenResponse
import com.example.demo.domain.auth.entity.RefreshToken
import com.example.demo.domain.auth.repository.RefreshTokenRepository
import com.example.demo.domain.user.repository.UserRepository
import com.example.demo.global.exception.ExceptionEnum
import com.example.demo.global.exception.NoNameException
import com.example.demo.global.security.jwt.JwtProvider
import com.example.demo.global.security.jwt.JwtType
import com.example.demo.global.security.jwt.dto.JwtDetails
import com.example.demo.global.util.UserUtil
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Duration
import java.time.LocalDateTime
import java.util.*

@Service
@Transactional
class ReissueTokenUsecase (
	private val jwtProvider: JwtProvider,
	private val refreshTokenRepository: RefreshTokenRepository,
) {
	fun execute(resolveRefreshToken: String): ReissueTokenResponse {
		val currentUserId = jwtProvider.getIdByRefreshToken(resolveRefreshToken)
		val savedRefreshToken = jwtProvider.getSavedRefreshTokenByRefreshToken(resolveRefreshToken)

		if(resolveRefreshToken != savedRefreshToken.refreshToken){
			throw NoNameException(ExceptionEnum.INVALID_REFRESH_TOKEN)
		}

		val newAccessToken = jwtProvider.generateToken(currentUserId, JwtType.ACCESS_TOKEN)
		val newRefreshToken = deleteRefreshTokenOrSave(currentUserId)

		refreshTokenRepository.save(RefreshToken(
			id = UUID.fromString(currentUserId),
			refreshToken = newRefreshToken.token,
			expires = jwtProvider.refreshTokenExpires
		))

		return ReissueTokenResponse(
			accessToken = newAccessToken.token,
			accessTokenExpiredAt = newAccessToken.expiredAt,
			refreshToken = newRefreshToken.token,
			refreshTokenExpiredAt = newRefreshToken.expiredAt
		)
	}

	fun deleteRefreshTokenOrSave(id: String): JwtDetails {
		val refreshToken = refreshTokenRepository.findById(UUID.fromString(id)).orElseThrow {
			NoNameException(ExceptionEnum.NOT_FOUND_REFRESH_TOKEN)
		}

		refreshTokenRepository.delete(refreshToken)
		val newRefreshToken = jwtProvider.generateToken(id, JwtType.REFRESH_TOKEN)

		val newRefreshTokenEntity = RefreshToken(
			id = UUID.fromString(id),
			refreshToken = newRefreshToken.token,
			expires = jwtProvider.refreshTokenExpires
		)

		refreshTokenRepository.save(newRefreshTokenEntity)

		return newRefreshToken
	}
}