package team.gsm.flooding.domain.auth.usecase

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import team.gsm.flooding.domain.auth.repository.VerifyCodeRepository
import team.gsm.flooding.domain.user.repository.UserRepository
import team.gsm.flooding.global.exception.ExceptionEnum
import team.gsm.flooding.global.exception.HttpException

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
