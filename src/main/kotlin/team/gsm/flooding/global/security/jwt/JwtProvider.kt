package team.gsm.flooding.global.security.jwt

import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.UnsupportedJwtException
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Component
import team.gsm.flooding.domain.auth.entity.RefreshToken
import team.gsm.flooding.domain.auth.repository.RefreshTokenRepository
import team.gsm.flooding.global.exception.ExceptionEnum
import team.gsm.flooding.global.exception.ExpectedException
import team.gsm.flooding.global.security.details.AuthDetailsService
import team.gsm.flooding.global.security.jwt.dto.JwtDetails
import team.gsm.flooding.global.util.toDate
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
	fun getAuthentication(token: String): UsernamePasswordAuthenticationToken {
		val userDetails = authDetailsService.loadUserByUsername(getPayload(token).subject)
		return UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
	}

	fun getSavedRefreshTokenByRefreshToken(refreshToken: String): RefreshToken {
		val userId = UUID.fromString(getPayload(refreshToken).subject)
		val savedRefreshToken =
			refreshTokenRepository.findById(userId).orElseThrow {
				ExpectedException(ExceptionEnum.NOT_FOUND_REFRESH_TOKEN)
			}

		return savedRefreshToken
	}

	fun getIdByRefreshToken(refreshToken: String): String = getPayload(refreshToken).subject

	fun getPayload(token: String?): Claims {
		if (token == null) {
			throw ExpectedException(ExceptionEnum.EMPTY_TOKEN)
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
			throw ExpectedException(ExceptionEnum.EXPIRED_TOKEN)
		} catch (e: UnsupportedJwtException) {
			throw ExpectedException(ExceptionEnum.UNSUPPORTED_TOKEN)
		} catch (e: MalformedJwtException) {
			throw ExpectedException(ExceptionEnum.MALFORMED_TOKEN)
		} catch (e: RuntimeException) {
			throw ExpectedException(ExceptionEnum.OTHER_TOKEN)
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
