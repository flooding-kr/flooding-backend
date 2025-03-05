package kr.flooding.backend.domain.auth.usecase

import kr.flooding.backend.domain.user.repository.UserRepository
import kr.flooding.backend.global.exception.ExceptionEnum
import kr.flooding.backend.global.exception.HttpException
import kr.flooding.backend.global.thirdparty.email.EmailAdapter
import kr.flooding.backend.global.util.PasswordUtil
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Duration

@Service
@Transactional
class RequestResetPasswordUsecase(
	private val redisTemplate: RedisTemplate<String, String>,
	private val userRepository: UserRepository,
	private val passwordUtil: PasswordUtil,
	private val emailAdapter: EmailAdapter,
) {
	fun execute(email: String) {
		val userByEmail =
			userRepository.findByEmail(email).orElseThrow {
				HttpException(ExceptionEnum.NOT_FOUND_USER)
			}
		val userId = requireNotNull(userByEmail.id)

		val newResetPasswordCode = passwordUtil.generateRandomCode(64)

		val key = "reset-password-code:$userId"
		redisTemplate.opsForList().leftPush(key, newResetPasswordCode)
		redisTemplate.opsForList().trim(key, 0, 7)
		redisTemplate.expire(key, Duration.ofMinutes(30))

		emailAdapter.sendResetPassword(email, newResetPasswordCode)
	}
}
