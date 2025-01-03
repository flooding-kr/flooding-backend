package team.gsm.flooding.domain.auth.usecase

import team.gsm.flooding.domain.auth.repository.VerifyCodeRepository
import team.gsm.flooding.domain.user.repository.UserRepository
import team.gsm.flooding.global.exception.ExceptionEnum
import team.gsm.flooding.global.exception.ExpectedException
import team.gsm.flooding.global.thirdparty.email.EmailAdapter
import team.gsm.flooding.global.util.PasswordUtil
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
			ExpectedException(ExceptionEnum.NOT_FOUND_USER)
		}
		val id = userByEmail.id
		requireNotNull(id) { "id cannot be null" }

		if(userByEmail.isVerified){
			throw ExpectedException(ExceptionEnum.ALREADY_VERIFY_EMAIL)
		}

		val verifyCodeEntity = verifyCodeRepository.findById(id).orElseThrow {
			ExpectedException(ExceptionEnum.NOT_FOUND_VERIFY_CODE)
		}

		val newVerifyCode = passwordUtil.generateSixRandomCode()
		emailAdapter.sendVerifyCode(email, newVerifyCode)

		verifyCodeRepository.save(verifyCodeEntity.copy(code = newVerifyCode))
	}
}