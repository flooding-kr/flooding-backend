package kr.flooding.backend.global.util

import kr.flooding.backend.domain.user.entity.User
import kr.flooding.backend.domain.user.repository.UserRepository
import kr.flooding.backend.global.exception.ExceptionEnum
import kr.flooding.backend.global.exception.HttpException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
class UserUtil(
	private val userRepository: UserRepository,
) {
	fun getUser(): User {
		val email = SecurityContextHolder.getContext().authentication.name
		return userRepository.findByEmail(email).orElseThrow {
			HttpException(ExceptionEnum.NOT_FOUND_USER)
		}
	}
}
