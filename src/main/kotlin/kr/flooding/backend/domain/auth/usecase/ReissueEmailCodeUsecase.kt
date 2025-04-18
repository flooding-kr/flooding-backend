package kr.flooding.backend.domain.auth.usecase

import kr.flooding.backend.domain.auth.persistence.entity.VerifyCode
import kr.flooding.backend.domain.auth.persistence.repository.VerifyCodeRepository
import kr.flooding.backend.domain.user.persistence.repository.jpa.UserJpaRepository
import kr.flooding.backend.global.exception.ExceptionEnum
import kr.flooding.backend.global.exception.HttpException
import kr.flooding.backend.global.exception.toPair
import kr.flooding.backend.global.thirdparty.email.EmailAdapter
import kr.flooding.backend.global.util.PasswordUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class ReissueEmailCodeUsecase(
	private val userJpaRepository: UserJpaRepository,
	private val verifyCodeRepository: VerifyCodeRepository,
	private val passwordUtil: PasswordUtil,
	private val emailAdapter: EmailAdapter,
) {
	fun execute(email: String) {
		val userByEmail =
			userJpaRepository.findByEmail(email).orElseThrow {
				HttpException(ExceptionEnum.USER.NOT_FOUND_USER.toPair())
			}
		val id = requireNotNull(userByEmail.id)

		if (userByEmail.emailVerifyStatus) {
			throw HttpException(ExceptionEnum.AUTH.ALREADY_VERIFY_EMAIL.toPair())
		}

		val newVerifyCode = passwordUtil.generateRandomCode(64)
		val verifyCodeEntity =
			verifyCodeRepository.findById(id).orElse(
				VerifyCode(id, newVerifyCode),
			)

		emailAdapter.sendVerifyCode(email, newVerifyCode)

		verifyCodeRepository.save(verifyCodeEntity.copy(code = newVerifyCode))
	}
}
