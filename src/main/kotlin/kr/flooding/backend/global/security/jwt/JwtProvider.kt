package kr.flooding.backend.global.security.jwt

import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.UnsupportedJwtException
import io.jsonwebtoken.security.Keys
import kr.flooding.backend.global.exception.ExceptionEnum
import kr.flooding.backend.global.exception.HttpException
import kr.flooding.backend.global.exception.toPair
import kr.flooding.backend.global.properties.JwtProperties
import kr.flooding.backend.global.security.details.AuthDetailsService
import kr.flooding.backend.global.security.jwt.dto.JwtDetails
import kr.flooding.backend.global.util.DateUtil.Companion.toDate
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
	private val jwtProperties: JwtProperties,
) {
	fun getAuthentication(token: String?): UsernamePasswordAuthenticationToken? {
		val resolvedToken = resolveToken(token)
		val payload = getPayload(resolvedToken, JwtType.ACCESS_TOKEN)

		val userDetails = authDetailsService.loadUserByUsername(payload.subject)

		return UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
	}

	fun resolveToken(token: String?): String? =
		if (token == null || !token.startsWith("Bearer ")) {
			null
		} else {
			token.substring(7)
		}

	fun getIdByRefreshToken(refreshToken: String): String = getPayload(refreshToken, JwtType.REFRESH_TOKEN).subject

	fun getPayload(
		token: String?,
		jwtType: JwtType,
	): Claims {
		if (token == null) {
			throw HttpException(ExceptionEnum.AUTH.EMPTY_TOKEN.toPair())
		}

		val tokenKey =
			if (jwtType == JwtType.ACCESS_TOKEN) jwtProperties.accessTokenKey
			else jwtProperties.refreshTokenKey

		val keyBytes = Base64.getEncoder().encode(tokenKey.encodeToByteArray())
		val signingKey = Keys.hmacShaKeyFor(keyBytes)

		try {
			return Jwts
				.parser()
				.verifyWith(signingKey)
				.build()
				.parseSignedClaims(token)
				.payload
		} catch (e: ExpiredJwtException) {
			throw HttpException(ExceptionEnum.AUTH.EXPIRED_TOKEN.toPair())
		} catch (e: UnsupportedJwtException) {
			throw HttpException(ExceptionEnum.AUTH.UNSUPPORTED_TOKEN.toPair())
		} catch (e: MalformedJwtException) {
			throw HttpException(ExceptionEnum.AUTH.MALFORMED_TOKEN.toPair())
		} catch (e: RuntimeException) {
			throw HttpException(ExceptionEnum.AUTH.OTHER_TOKEN.toPair())
		}
	}

	fun generateToken(
		id: UUID,
		jwtType: JwtType,
	): JwtDetails {
		val tokenExpires =
			if (jwtType == JwtType.ACCESS_TOKEN) jwtProperties.accessTokenExpires
			else jwtProperties.refreshTokenExpires
		val expiredAt =
			LocalDateTime.now().plus(
				Duration.ofMillis(tokenExpires),
			)

		val tokenKey =
			if (jwtType == JwtType.ACCESS_TOKEN) jwtProperties.accessTokenKey
			else jwtProperties.refreshTokenKey

		val keyBytes = Base64.getEncoder().encode(tokenKey.encodeToByteArray())
		val signingKey = Keys.hmacShaKeyFor(keyBytes)

		val token =
			Jwts
				.builder()
				.subject(id.toString())
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
