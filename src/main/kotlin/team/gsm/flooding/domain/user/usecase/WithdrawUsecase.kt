package team.gsm.flooding.domain.user.usecase

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import team.gsm.flooding.domain.auth.repository.RefreshTokenRepository
import team.gsm.flooding.domain.user.repository.UserRepository
import team.gsm.flooding.global.exception.ExceptionEnum
import team.gsm.flooding.global.exception.ExpectedException
import team.gsm.flooding.global.util.UserUtil

@Service
@Transactional
class WithdrawUsecase(
	private val userRepository: UserRepository,
	private val refreshTokenRepository: RefreshTokenRepository,
	private val userUtil: UserUtil,
	private val passwordEncoder: PasswordEncoder,
) {
	fun execute(password: String) {
		val savedUser = userUtil.getUser()
		val isComparePassword = passwordEncoder.matches(password, savedUser.encodedPassword)
		val id = savedUser.id

		requireNotNull(id) { "id cannot be null" }

		if (!isComparePassword) {
			throw ExpectedException(ExceptionEnum.WRONG_PASSWORD)
		}

		refreshTokenRepository.deleteById(id)
		userRepository.delete(savedUser)
	}
}
