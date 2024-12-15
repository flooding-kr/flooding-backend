package com.example.demo.domain.auth.usecase

import com.example.demo.domain.auth.dto.request.SignUpRequest
import com.example.demo.domain.auth.entity.VerifyCode
import com.example.demo.domain.auth.repository.VerifyCodeRepository
import com.example.demo.domain.user.entity.Role
import com.example.demo.domain.user.entity.StudentInfo
import com.example.demo.domain.user.entity.User
import com.example.demo.domain.user.repository.UserRepository
import com.example.demo.global.exception.ExceptionEnum
import com.example.demo.global.exception.NoNameException
import com.example.demo.global.thirdparty.email.EmailAdapter
import com.example.demo.global.util.PasswordUtil
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SignUpUsecase (
	private val userRepository: UserRepository,
	private val verifyCodeRepository: VerifyCodeRepository,
	private val passwordEncoder: PasswordEncoder,
	private val emailAdapter: EmailAdapter,
	private val passwordUtil: PasswordUtil,
) {
	@Transactional
	fun execute(signUpRequest: SignUpRequest){
		val encodedPassword = passwordEncoder.encode(signUpRequest.password)
		val studentInfo = getStudentInfo(signUpRequest.schoolNumber)
		val email = signUpRequest.email

		if(userRepository.existsByEmail(email))
			throw NoNameException(ExceptionEnum.DUPLICATED_EMAIL)

		if(userRepository.existsByStudentInfo(studentInfo))
			throw NoNameException(ExceptionEnum.DUPLICATED_STUDENT_INFO)

		val user = userRepository.save(User(
			email = email,
			encodedPassword = encodedPassword,
			studentInfo = studentInfo,
			roles = mutableListOf(Role.ROLE_USER),
			isVerified = false
		))

		val id = user.id
		requireNotNull(id) { "id cannot be null" }

		val randomVerifyCode = passwordUtil.generateSixRandomCode()
		emailAdapter.sendVerifyCode(email, randomVerifyCode)

		val verifyCode = VerifyCode(
			id = id,
			code = randomVerifyCode,
			expiredInMinutes = 15
		)
		verifyCodeRepository.save(verifyCode)
	}

	private fun getStudentInfo(schoolInfoString: String): StudentInfo {
		return StudentInfo(
			grade = schoolInfoString[0].digitToInt(),
			classroom = schoolInfoString[1].digitToInt(),
			number = schoolInfoString.slice(2..3).toInt()
		)
	}
}