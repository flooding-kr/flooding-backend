package team.gsm.flooding.domain.auth.dto.response

import java.time.LocalDateTime

data class ReissueTokenResponse (
	val accessToken: String,
	val accessTokenExpiredAt: LocalDateTime,
	val refreshToken: String,
	val refreshTokenExpiredAt: LocalDateTime
)