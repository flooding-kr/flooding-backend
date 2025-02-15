package team.gsm.flooding.domain.auth.usecase

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
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
import team.gsm.flooding.global.util.StudentUtil.Companion.calcGradeToYear

@Service
class SignUpUsecase(
	private val userRepository: UserRepository,
	private val verifyCodeRepository: VerifyCodeRepository,
	private val passwordEncoder: PasswordEncoder,
	private val emailAdapter: EmailAdapter,
	private val passwordUtil: PasswordUtil,
) {
	@Transactional
	fun execute(request: SignUpRequest) {
		val encodedPassword = passwordEncoder.encode(request.password)

		if (userRepository.existsByEmail(request.email)) {
			throw ExpectedException(ExceptionEnum.DUPLICATED_EMAIL)
		}

		val maxYear = calcGradeToYear(1)
		if (request.year > maxYear) {
			throw ExpectedException(ExceptionEnum.WRONG_YEAR)
		}

		val studentInfo =
			StudentInfo(
				year = request.year,
				classroom = request.classroom,
				number = request.number,
			)

		if (userRepository.existsByStudentInfo(studentInfo)) {
			throw ExpectedException(ExceptionEnum.DUPLICATED_STUDENT_INFO)
		}

		val user =
			userRepository.save(
				User(
					email = request.email,
					encodedPassword = encodedPassword,
					studentInfo = studentInfo,
					roles = mutableListOf(Role.ROLE_USER),
					isVerified = false,
					gender = request.gender,
					name = request.name,
				),
			)

		val id = user.id
		requireNotNull(id) { "id cannot be null" }

		val randomVerifyCode = passwordUtil.generateSixRandomCode()
		emailAdapter.sendVerifyCode(request.email, randomVerifyCode)

		val verifyCode =
			VerifyCode(
				id = id,
				code = randomVerifyCode,
				expiredInMinutes = 15,
			)
		verifyCodeRepository.save(verifyCode)
	}
}
