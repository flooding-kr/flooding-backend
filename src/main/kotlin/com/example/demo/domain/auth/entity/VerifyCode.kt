package com.example.demo.domain.auth.entity

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.TimeToLive
import java.util.*
import java.util.concurrent.TimeUnit

@RedisHash("verify-code")
data class VerifyCode (
	@Id
	val id: UUID,

	val code: String,

	@TimeToLive(unit = TimeUnit.MINUTES)
	val expiredInMinutes: Long
)