package kr.flooding.backend.domain.auth.entity

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import java.util.UUID

@RedisHash("verify-code", timeToLive = 1800)
data class VerifyCode(
	@Id
	val id: UUID,

	val code: String,
)
