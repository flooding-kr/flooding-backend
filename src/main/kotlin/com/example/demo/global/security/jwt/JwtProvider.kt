package com.example.demo.global.security.jwt

import com.example.demo.domain.auth.entity.RefreshToken
import com.example.demo.domain.auth.repository.RefreshTokenRepository
import com.example.demo.global.exception.ExceptionEnum
import com.example.demo.global.exception.NoNameException
import com.example.demo.global.security.details.AuthDetailsService
import com.example.demo.global.security.jwt.dto.JwtDetails
import com.example.demo.global.util.toDate
import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.UnsupportedJwtException
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Component
import java.time.Duration
import java.time.LocalDateTime
import java.util.Base64
import java.util.Date
import java.util.UUID
import kotlin.math.exp

@Component
class JwtProvider (
	private val authDetailsService: AuthDetailsService,
	private val refreshTokenRepository: RefreshTokenRepository,

	@Value("\${jwt.access-token-key}")
	private val accessTokenKey: String,
	@Value("\${jwt.access-token-expires}")
	private val accessTokenExpires: Long,
	@Value("\${jwt.refresh-token-expires}")
	val refreshTokenExpires: Long,
){
	fun getAuthentication(token: String): UsernamePasswordAuthenticationToken {
		val userDetails = authDetailsService.loadUserByUsername(getPayload(token).subject)
		return UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
	}

	fun getSavedRefreshTokenByRefreshToken(refreshToken: String): RefreshToken {
		val userId = UUID.fromString(getPayload(refreshToken).subject)
		val savedRefreshToken = refreshTokenRepository.findById(userId).orElseThrow {
			NoNameException(ExceptionEnum.NOT_FOUND_REFRESH_TOKEN)
		}

		return savedRefreshToken
	}

	fun getIdByRefreshToken(refreshToken: String): String {
		return getPayload(refreshToken).subject
	}

	fun getPayload(token: String?): Claims {
		if(token == null){
			throw NoNameException(ExceptionEnum.EMPTY_TOKEN)
		}

		val keyBytes = Base64.getEncoder().encode(accessTokenKey.encodeToByteArray())
		val signingKey = Keys.hmacShaKeyFor(keyBytes)

		try {
			return Jwts.parser()
				.verifyWith(signingKey)
				.build()
				.parseSignedClaims(token)
				.payload
		} catch (e: ExpiredJwtException) {
			throw NoNameException(ExceptionEnum.EXPIRED_TOKEN)
		} catch (e: UnsupportedJwtException) {
			throw NoNameException(ExceptionEnum.UNSUPPORTED_TOKEN)
		} catch (e: MalformedJwtException) {
			throw NoNameException(ExceptionEnum.MALFORMED_TOKEN)
		} catch (e: RuntimeException) {
			throw NoNameException(ExceptionEnum.OTHER_TOKEN)
		}
	}

	fun generateToken(id: String, jwtType: JwtType): JwtDetails {
		val isAccessToken = jwtType == JwtType.ACCESS_TOKEN
		val expiredAt = LocalDateTime.now().plus(Duration.ofMillis(
			if(isAccessToken) accessTokenExpires
			else refreshTokenExpires)
		)

		val keyBytes = Base64.getEncoder().encode(accessTokenKey.encodeToByteArray())
		val signingKey = Keys.hmacShaKeyFor(keyBytes)

		val token = Jwts.builder()
			.subject(id)
			.signWith(signingKey)
			.issuedAt(Date())
			.expiration(expiredAt.toDate())
			.compact()

		return JwtDetails(
			token = token,
			expiredAt = expiredAt
		)
	}
}