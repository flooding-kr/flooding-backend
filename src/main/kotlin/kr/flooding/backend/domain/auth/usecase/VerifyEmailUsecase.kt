package kr.flooding.backend.domain.auth.usecase

import kr.flooding.backend.domain.auth.repository.VerifyCodeRepository
import kr.flooding.backend.domain.user.repository.UserRepository
import kr.flooding.backend.global.exception.ExceptionEnum
import kr.flooding.backend.global.exception.HttpException
import kr.flooding.backend.global.exception.toPair
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class VerifyEmailUsecase(
	private val userRepository: UserRepository,
	private val verifyCodeRepository: VerifyCodeRepository,
) {
	fun execute(
		email: String,
		code: String,
	) {
		val user =
			userRepository.findByEmail(email).orElseThrow {
				HttpException(ExceptionEnum.USER.NOT_FOUND_USER.toPair())
			}
		val id = user.id
		requireNotNull(id) { "id cannot be null" }

		if (user.emailVerifyStatus) {
			throw HttpException(ExceptionEnum.AUTH.ALREADY_VERIFY_EMAIL.toPair())
		}

		val verifyCodeEntity =
			verifyCodeRepository.findById(id).orElseThrow {
				HttpException(ExceptionEnum.AUTH.NOT_FOUND_VERIFY_CODE.toPair())
			}

		if (verifyCodeEntity.code != code) {
			throw HttpException(ExceptionEnum.AUTH.NOT_FOUND_VERIFY_CODE.toPair())
		}

		user.enableEmailVerify()
	}
}
