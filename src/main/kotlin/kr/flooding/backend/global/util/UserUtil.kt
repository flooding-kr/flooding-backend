package kr.flooding.backend.global.util

import kr.flooding.backend.domain.user.persistence.entity.User
import kr.flooding.backend.domain.user.persistence.repository.jpa.UserJpaRepository
import kr.flooding.backend.global.exception.ExceptionEnum
import kr.flooding.backend.global.exception.HttpException
import kr.flooding.backend.global.exception.toPair
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
class UserUtil(
	private val userJpaRepository: UserJpaRepository,
) {
	fun getUser(): User {
		val email = SecurityContextHolder.getContext().authentication.name
		return userJpaRepository.findByEmail(email).orElseThrow {
			HttpException(ExceptionEnum.USER.NOT_FOUND_USER.toPair())
		}
	}
}
