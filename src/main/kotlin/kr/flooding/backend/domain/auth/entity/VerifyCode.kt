package kr.flooding.backend.domain.auth.entity

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.TimeToLive
import java.util.UUID
import java.util.concurrent.TimeUnit

@RedisHash("verify-code")
data class VerifyCode(
	@Id
	val id: UUID,

	val code: String,

	@TimeToLive(unit = TimeUnit.MINUTES)
	val expiredInMinutes: Long,
)
