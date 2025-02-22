package kr.flooding.backend.domain.auth.entity

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.TimeToLive
import java.util.UUID
import java.util.concurrent.TimeUnit

@RedisHash("refresh-token")
data class RefreshToken(
	@Id
	val id: UUID,

	val refreshToken: String,

	@TimeToLive(unit = TimeUnit.MILLISECONDS)
	val expires: Long,
)
