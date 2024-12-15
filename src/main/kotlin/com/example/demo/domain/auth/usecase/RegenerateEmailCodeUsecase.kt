package com.example.demo.domain.auth.usecase

import com.example.demo.domain.auth.repository.VerifyCodeRepository
import com.example.demo.domain.user.repository.UserRepository
import com.example.demo.global.exception.ExceptionEnum
import com.example.demo.global.exception.NoNameException
import com.example.demo.global.thirdparty.email.EmailAdapter
import com.example.demo.global.util.PasswordUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class RegenerateEmailCodeUsecase (
	private val userRepository: UserRepository,
	private val verifyCodeRepository: VerifyCodeRepository,
	private val passwordUtil: PasswordUtil,
	private val emailAdapter: EmailAdapter
) {
	fun execute(email: String) {
		val userByEmail = userRepository.findByEmail(email).orElseThrow {
			NoNameException(ExceptionEnum.NOT_FOUND_USER)
		}
		val id = userByEmail.id
		requireNotNull(id) { "id cannot be null" }

		if(userByEmail.isVerified){
			throw NoNameException(ExceptionEnum.ALREADY_VERIFY_EMAIL)
		}

		val verifyCodeEntity = verifyCodeRepository.findById(id).orElseThrow {
			NoNameException(ExceptionEnum.NOT_FOUND_VERIFY_CODE)
		}

		val newVerifyCode = passwordUtil.generateSixRandomCode()
		emailAdapter.sendVerifyCode(email, newVerifyCode)

		verifyCodeRepository.save(verifyCodeEntity.copy(code = newVerifyCode))
	}
}