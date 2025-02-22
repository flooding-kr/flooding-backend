package kr.flooding.backend.domain.auth.usecase

import kr.flooding.backend.domain.auth.repository.VerifyCodeRepository
import kr.flooding.backend.domain.user.repository.UserRepository
import kr.flooding.backend.global.exception.ExceptionEnum
import kr.flooding.backend.global.exception.HttpException
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
				HttpException(ExceptionEnum.NOT_FOUND_USER)
			}
		val id = user.id
		requireNotNull(id) { "id cannot be null" }

		if (user.isVerified) {
			throw HttpException(ExceptionEnum.ALREADY_VERIFY_EMAIL)
		}

		val verifyCodeEntity =
			verifyCodeRepository.findById(id).orElseThrow {
				HttpException(ExceptionEnum.NOT_FOUND_VERIFY_CODE)
			}

		if (verifyCodeEntity.code != code) {
			throw HttpException(ExceptionEnum.NOT_FOUND_VERIFY_CODE)
		}

		val updatedUser = user.copy(isVerified = true)
		userRepository.save(updatedUser)
	}
}
