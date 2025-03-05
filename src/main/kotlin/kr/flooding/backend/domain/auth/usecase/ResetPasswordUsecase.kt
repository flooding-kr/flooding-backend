package kr.flooding.backend.domain.auth.usecase

import kr.flooding.backend.domain.auth.dto.request.ResetPasswordRequest
import kr.flooding.backend.domain.user.repository.UserRepository
import kr.flooding.backend.global.exception.ExceptionEnum
import kr.flooding.backend.global.exception.HttpException
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class ResetPasswordUsecase(
	private val redisTemplate: RedisTemplate<String, String>,
	private val userRepository: UserRepository,
) {
	fun execute(request: ResetPasswordRequest) {
		val userByEmail =
			userRepository.findByEmail(request.email).orElseThrow {
				HttpException(ExceptionEnum.NOT_FOUND_USER)
			}
		val userId = requireNotNull(userByEmail.id)

		val key = "reset-password-code:$userId"
		val requestList = redisTemplate.opsForList().range(key, 0, -1)
		if (requestList.isNullOrEmpty() || !requestList.contains(request.code)) {
			throw HttpException(ExceptionEnum.NOT_FOUND_RESET_PASSWORD_REQUEST_CODE)
		}

		redisTemplate.delete(key)

		userByEmail.resetPassword(request.password)
	}
}
