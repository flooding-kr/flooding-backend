package kr.flooding.backend.domain.auth.usecase

import kr.flooding.backend.domain.auth.entity.VerifyCode
import kr.flooding.backend.domain.auth.repository.VerifyCodeRepository
import kr.flooding.backend.domain.user.repository.UserRepository
import kr.flooding.backend.global.exception.ExceptionEnum
import kr.flooding.backend.global.exception.HttpException
import kr.flooding.backend.global.thirdparty.email.EmailAdapter
import kr.flooding.backend.global.util.PasswordUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class ReissueEmailCodeUsecase(
	private val userRepository: UserRepository,
	private val verifyCodeRepository: VerifyCodeRepository,
	private val passwordUtil: PasswordUtil,
	private val emailAdapter: EmailAdapter,
) {
	fun execute(email: String) {
		val userByEmail =
			userRepository.findByEmail(email).orElseThrow {
				HttpException(ExceptionEnum.NOT_FOUND_USER)
			}
		val id = requireNotNull(userByEmail.id)

		if (userByEmail.isVerified) {
			throw HttpException(ExceptionEnum.ALREADY_VERIFY_EMAIL)
		}

		val newVerifyCode = passwordUtil.generateRandomCode(6)
		val verifyCodeEntity =
			verifyCodeRepository.findById(id).orElse(
				VerifyCode(id, newVerifyCode),
			)

		emailAdapter.sendVerifyCode(email, newVerifyCode)

		verifyCodeRepository.save(verifyCodeEntity.copy(code = newVerifyCode))
	}
}
