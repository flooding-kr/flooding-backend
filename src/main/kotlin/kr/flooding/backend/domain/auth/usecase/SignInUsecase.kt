package kr.flooding.backend.domain.auth.usecase

import kr.flooding.backend.domain.auth.dto.request.SignInRequest
import kr.flooding.backend.domain.auth.dto.response.SignInResponse
import kr.flooding.backend.domain.auth.entity.RefreshToken
import kr.flooding.backend.domain.auth.repository.RefreshTokenRepository
import kr.flooding.backend.domain.user.enums.UserState
import kr.flooding.backend.domain.user.repository.UserRepository
import kr.flooding.backend.global.exception.ExceptionEnum
import kr.flooding.backend.global.exception.HttpException
import kr.flooding.backend.global.exception.toPair
import kr.flooding.backend.global.security.jwt.JwtProvider
import kr.flooding.backend.global.security.jwt.JwtType
import kr.flooding.backend.global.security.jwt.dto.JwtDetails
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Duration
import java.time.LocalDateTime
import java.util.UUID

@Service
@Transactional
class SignInUsecase(
	private val userRepository: UserRepository,
	private val passwordEncoder: PasswordEncoder,
	private val jwtProvider: JwtProvider,
	private val refreshTokenRepository: RefreshTokenRepository,
) {
	fun execute(signInRequest: SignInRequest): SignInResponse {
		val email = signInRequest.email
		val user =
			userRepository.findByEmail(email).orElseThrow {
				HttpException(ExceptionEnum.USER.NOT_FOUND_USER.toPair())
			}

		if (!user.emailVerifyStatus) {
			throw HttpException(ExceptionEnum.AUTH.NOT_VERIFIED_EMAIL.toPair())
		}

		if (user.userState != UserState.APPROVED) {
			throw HttpException(ExceptionEnum.AUTH.NOT_APPROVED_USER.toPair())
		}

		val id = user.id
		requireNotNull(id) { "id cannot be null" }

		val rawPassword = signInRequest.password
		val encodedPassword = user.encodedPassword

		if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
			throw HttpException(ExceptionEnum.AUTH.WRONG_PASSWORD.toPair())
		}

		val accessToken = jwtProvider.generateToken(id, JwtType.ACCESS_TOKEN)
		val refreshToken = getRefreshTokenOrSave(id)

		return SignInResponse(
			accessToken = accessToken.token,
			accessTokenExpiredAt = accessToken.expiredAt,
			refreshToken = refreshToken.token,
			refreshTokenExpiredAt = refreshToken.expiredAt,
		)
	}

	fun getRefreshTokenOrSave(id: UUID): JwtDetails {
		val refreshToken = refreshTokenRepository.findById(id)

		if (refreshToken.isEmpty) {
			val newRefreshToken = jwtProvider.generateToken(id, JwtType.REFRESH_TOKEN)
			refreshTokenRepository.save(
				RefreshToken(
					id = id,
					refreshToken = newRefreshToken.token,
					expires = jwtProvider.refreshTokenExpires,
				),
			)
			return newRefreshToken
		} else {
			return JwtDetails(
				token = refreshToken.get().refreshToken,
				expiredAt = LocalDateTime.now().plus(Duration.ofMillis(refreshToken.get().expires)),
			)
		}
	}
}
