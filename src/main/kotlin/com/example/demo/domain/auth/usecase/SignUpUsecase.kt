package com.example.demo.domain.auth.usecase

import com.example.demo.domain.auth.dto.request.SignUpRequest
import com.example.demo.domain.user.entity.Role
import com.example.demo.domain.user.entity.StudentInfo
import com.example.demo.domain.user.entity.User
import com.example.demo.domain.user.repository.UserRepository
import com.example.demo.global.exception.ExceptionEnum
import com.example.demo.global.exception.NoNameException
import jakarta.websocket.MessageHandler.Partial
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional
class SignUpUsecase (
	private val userRepository: UserRepository,
	private val passwordEncoder: PasswordEncoder
) {
	fun execute(signUpRequest: SignUpRequest){
		val encodedPassword = passwordEncoder.encode(signUpRequest.password)
		val studentInfo = getStudentInfo(signUpRequest.schoolNumber)
		val email = signUpRequest.email

		if(userRepository.existsByEmail(email))
			throw NoNameException(ExceptionEnum.DUPLICATED_EMAIL)

		if(userRepository.existsByStudentInfo(studentInfo))
			throw NoNameException(ExceptionEnum.DUPLICATED_STUDENT_INFO)

		val user = User(
			id = null,
			email = email,
			encodedPassword = encodedPassword,
			studentInfo = studentInfo,
			roles = mutableListOf(Role.ROLE_USER)
		)

		userRepository.save(user)
	}

	private fun getStudentInfo(schoolInfoString: String): StudentInfo {
		return StudentInfo(
			grade = schoolInfoString[0].digitToInt(),
			classroom = schoolInfoString[1].digitToInt(),
			number = schoolInfoString.slice(2..3).toInt()
		)
	}
}