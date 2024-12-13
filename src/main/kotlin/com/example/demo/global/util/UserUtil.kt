package com.example.demo.global.util

import com.example.demo.domain.user.entity.User
import com.example.demo.domain.user.repository.UserRepository
import com.example.demo.global.exception.ExceptionEnum
import com.example.demo.global.exception.NoNameException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
class UserUtil (
	private val userRepository: UserRepository
) {
	fun getUser(): User {
		val email = SecurityContextHolder.getContext().authentication.name
		return userRepository.findByEmail(email).orElseThrow {
			NoNameException(ExceptionEnum.NOT_FOUND_USER)
		}
	}
}