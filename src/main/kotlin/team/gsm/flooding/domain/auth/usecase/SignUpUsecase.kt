package team.gsm.flooding.domain.auth.usecase

import team.gsm.flooding.domain.auth.dto.request.SignUpRequest
import team.gsm.flooding.domain.auth.entity.VerifyCode
import team.gsm.flooding.domain.auth.repository.VerifyCodeRepository
import team.gsm.flooding.domain.user.entity.Role
import team.gsm.flooding.domain.user.entity.StudentInfo
import team.gsm.flooding.domain.user.entity.User
import team.gsm.flooding.domain.user.repository.UserRepository
import team.gsm.flooding.global.exception.ExceptionEnum
import team.gsm.flooding.global.exception.ExpectedException
import team.gsm.flooding.global.thirdparty.email.EmailAdapter
import team.gsm.flooding.global.util.PasswordUtil
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
			throw ExpectedException(ExceptionEnum.DUPLICATED_EMAIL)

		if(userRepository.existsByStudentInfo(studentInfo))
			throw ExpectedException(ExceptionEnum.DUPLICATED_STUDENT_INFO)

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