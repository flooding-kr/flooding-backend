package team.gsm.flooding.domain.auth.usecase

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import team.gsm.flooding.domain.auth.entity.VerifyCode
import team.gsm.flooding.domain.auth.repository.VerifyCodeRepository
import team.gsm.flooding.domain.user.repository.UserRepository
import team.gsm.flooding.global.exception.ExceptionEnum
import team.gsm.flooding.global.exception.ExpectedException
import team.gsm.flooding.global.thirdparty.email.EmailAdapter
import team.gsm.flooding.global.util.PasswordUtil

@Service
@Transactional
class RegenerateEmailCodeUsecase(
	private val userRepository: UserRepository,
	private val verifyCodeRepository: VerifyCodeRepository,
	private val passwordUtil: PasswordUtil,
	private val emailAdapter: EmailAdapter,
) {
	fun execute(email: String) {
		val userByEmail =
			userRepository.findByEmail(email).orElseThrow {
				ExpectedException(ExceptionEnum.NOT_FOUND_USER)
			}
		val id = requireNotNull(userByEmail.id)

		if (userByEmail.isVerified) {
			throw ExpectedException(ExceptionEnum.ALREADY_VERIFY_EMAIL)
		}

		val newVerifyCode = passwordUtil.generateSixRandomCode()
		val verifyCodeEntity =
			verifyCodeRepository.findById(id).orElse(
				VerifyCode(id, newVerifyCode, 15),
			)

		emailAdapter.sendVerifyCode(email, newVerifyCode)

		verifyCodeRepository.save(verifyCodeEntity.copy(code = newVerifyCode))
	}
}
