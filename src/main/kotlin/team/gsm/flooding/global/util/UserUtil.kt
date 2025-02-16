package team.gsm.flooding.global.util

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import team.gsm.flooding.domain.user.entity.User
import team.gsm.flooding.domain.user.repository.UserRepository
import team.gsm.flooding.global.exception.ExceptionEnum
import team.gsm.flooding.global.exception.HttpException

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
