package kr.flooding.backend.domain.auth.usecase

import kr.flooding.backend.domain.auth.dto.request.ResetPasswordRequest
import kr.flooding.backend.domain.user.repository.UserRepository
import kr.flooding.backend.global.exception.ExceptionEnum
import kr.flooding.backend.global.exception.HttpException
import kr.flooding.backend.global.exception.toPair
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class ResetPasswordUsecase(
	private val redisTemplate: RedisTemplate<String, String>,
	private val userRepository: UserRepository,
	private val passwordEncoder: PasswordEncoder,
) {
	fun execute(request: ResetPasswordRequest) {
		val userByEmail =
			userRepository.findByEmail(request.email).orElseThrow {
				HttpException(ExceptionEnum.USER.NOT_FOUND_USER.toPair())
			}
		val userId = requireNotNull(userByEmail.id)

		val key = "reset-password-code:$userId"
		val requestList = redisTemplate.opsForList().range(key, 0, -1)
		if (requestList.isNullOrEmpty() || !requestList.contains(request.code)) {
			throw HttpException(ExceptionEnum.AUTH.NOT_FOUND_RESET_PASSWORD_REQUEST_CODE.toPair())
		}

		redisTemplate.delete(key)

		val encodedPassword = passwordEncoder.encode(request.password)
		userByEmail.resetPassword(encodedPassword)
	}
}
