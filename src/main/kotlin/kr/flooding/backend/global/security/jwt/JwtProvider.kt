package kr.flooding.backend.global.security.jwt

import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.UnsupportedJwtException
import io.jsonwebtoken.security.Keys
import kr.flooding.backend.domain.auth.entity.RefreshToken
import kr.flooding.backend.domain.auth.repository.RefreshTokenRepository
import kr.flooding.backend.global.exception.ExceptionEnum
import kr.flooding.backend.global.exception.HttpException
import kr.flooding.backend.global.security.details.AuthDetailsService
import kr.flooding.backend.global.security.jwt.dto.JwtDetails
import kr.flooding.backend.global.util.toDate
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Component
import java.time.Duration
import java.time.LocalDateTime
import java.util.Base64
import java.util.Date
import java.util.UUID

@Component
class JwtProvider(
	private val authDetailsService: AuthDetailsService,
	private val refreshTokenRepository: RefreshTokenRepository,
	@Value("\${jwt.access-token-key}")
	private val accessTokenKey: String,
	@Value("\${jwt.access-token-expires}")
	private val accessTokenExpires: Long,
	@Value("\${jwt.refresh-token-expires}")
	val refreshTokenExpires: Long,
) {
	fun getAuthentication(token: String?): UsernamePasswordAuthenticationToken? {
		val resolvedToken = resolveToken(token)
		val payload = getPayload(resolvedToken)

		val userDetails = authDetailsService.loadUserByUsername(payload.subject)

		return UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
	}

	fun resolveToken(token: String?): String? =
		if (token == null || !token.startsWith("Bearer ")) {
			null
		} else {
			token.substring(7)
		}

	fun getSavedRefreshTokenByRefreshToken(refreshToken: String): RefreshToken {
		val userId = UUID.fromString(getPayload(refreshToken).subject)
		val savedRefreshToken =
			refreshTokenRepository.findById(userId).orElseThrow {
				HttpException(ExceptionEnum.NOT_FOUND_REFRESH_TOKEN)
			}

		return savedRefreshToken
	}

	fun getIdByRefreshToken(refreshToken: String): String = getPayload(refreshToken).subject

	fun getPayload(token: String?): Claims {
		if (token == null) {
			throw HttpException(ExceptionEnum.EMPTY_TOKEN)
		}

		val keyBytes = Base64.getEncoder().encode(accessTokenKey.encodeToByteArray())
		val signingKey = Keys.hmacShaKeyFor(keyBytes)

		try {
			return Jwts
				.parser()
				.verifyWith(signingKey)
				.build()
				.parseSignedClaims(token)
				.payload
		} catch (e: ExpiredJwtException) {
			throw HttpException(ExceptionEnum.EXPIRED_TOKEN)
		} catch (e: UnsupportedJwtException) {
			throw HttpException(ExceptionEnum.UNSUPPORTED_TOKEN)
		} catch (e: MalformedJwtException) {
			throw HttpException(ExceptionEnum.MALFORMED_TOKEN)
		} catch (e: RuntimeException) {
			throw HttpException(ExceptionEnum.OTHER_TOKEN)
		}
	}

	fun generateToken(
		id: String,
		jwtType: JwtType,
	): JwtDetails {
		val isAccessToken = jwtType == JwtType.ACCESS_TOKEN
		val expiredAt =
			LocalDateTime.now().plus(
				Duration.ofMillis(
					if (isAccessToken) {
						accessTokenExpires
					} else {
						refreshTokenExpires
					},
				),
			)

		val keyBytes = Base64.getEncoder().encode(accessTokenKey.encodeToByteArray())
		val signingKey = Keys.hmacShaKeyFor(keyBytes)

		val token =
			Jwts
				.builder()
				.subject(id)
				.signWith(signingKey)
				.issuedAt(Date())
				.expiration(expiredAt.toDate())
				.compact()

		return JwtDetails(
			token = token,
			expiredAt = expiredAt,
		)
	}
}
