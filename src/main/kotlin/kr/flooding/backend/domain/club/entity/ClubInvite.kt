package kr.flooding.backend.domain.club.entity

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import java.util.UUID

// 1일 뒤에 만료
@RedisHash(value = "club-invite-code", timeToLive = 86400)
data class ClubInvite(
	@Id
	val userId: UUID,

	val code: String,

	val clubId: UUID,
)
