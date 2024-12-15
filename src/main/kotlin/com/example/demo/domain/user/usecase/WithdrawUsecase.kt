package com.example.demo.domain.user.usecase

import com.example.demo.domain.auth.repository.RefreshTokenRepository
import com.example.demo.domain.user.repository.UserRepository
import com.example.demo.global.exception.ExceptionEnum
import com.example.demo.global.exception.NoNameException
import com.example.demo.global.util.UserUtil
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class WithdrawUsecase (
	private val userRepository: UserRepository,
	private val refreshTokenRepository: RefreshTokenRepository,
	private val userUtil: UserUtil,
	private val passwordEncoder: PasswordEncoder
) {
	fun execute(password: String) {
		val savedUser = userUtil.getUser()
		val isComparePassword = passwordEncoder.matches(password, savedUser.encodedPassword)
		val id = savedUser.id

		requireNotNull(id) { "id cannot be null" }

		if(!isComparePassword){
			throw NoNameException(ExceptionEnum.WRONG_PASSWORD)
		}

		refreshTokenRepository.deleteById(id)
		userRepository.delete(savedUser)
	}
}