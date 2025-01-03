package team.gsm.flooding.global.util

import team.gsm.flooding.domain.user.entity.User
import team.gsm.flooding.domain.user.repository.UserRepository
import team.gsm.flooding.global.exception.ExceptionEnum
import team.gsm.flooding.global.exception.ExpectedException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
class UserUtil (
	private val userRepository: UserRepository
) {
	fun getUser(): User {
		val email = SecurityContextHolder.getContext().authentication.name
		return userRepository.findByEmail(email).orElseThrow {
			ExpectedException(ExceptionEnum.NOT_FOUND_USER)
		}
	}
}