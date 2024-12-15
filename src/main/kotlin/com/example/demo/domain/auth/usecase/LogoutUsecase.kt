package com.example.demo.domain.auth.usecase

import com.example.demo.domain.auth.dto.request.SignInRequest
import com.example.demo.domain.auth.dto.request.VerifyEmailRequest
import com.example.demo.domain.auth.dto.response.SignInResponse
import com.example.demo.domain.auth.repository.RefreshTokenRepository
import com.example.demo.domain.auth.repository.VerifyCodeRepository
import com.example.demo.domain.user.repository.UserRepository
import com.example.demo.global.exception.ExceptionEnum
import com.example.demo.global.exception.NoNameException
import com.example.demo.global.security.jwt.JwtProvider
import com.example.demo.global.security.jwt.JwtType
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional
class VerifyEmailUsecase (
	private val userRepository: UserRepository,
	private val verifyCodeRepository: VerifyCodeRepository
) {
	fun execute(email: String, code: String) {
		val user = userRepository.findByEmail(email).orElseThrow {
			NoNameException(ExceptionEnum.NOT_FOUND_USER)
		}
		val id = user.id
		requireNotNull(id) { "id cannot be null" }


		if(user.isVerified){
			throw NoNameException(ExceptionEnum.ALREADY_VERIFY_EMAIL)
		}

		val verifyCodeEntity = verifyCodeRepository.findById(id).orElseThrow {
			NoNameException(ExceptionEnum.NOT_FOUND_VERIFY_CODE)
		}

		if(verifyCodeEntity.code != code){
			throw NoNameException(ExceptionEnum.NOT_FOUND_VERIFY_CODE)
		}

		val updatedUser = user.copy(isVerified = true)
		userRepository.save(updatedUser)
	}
}