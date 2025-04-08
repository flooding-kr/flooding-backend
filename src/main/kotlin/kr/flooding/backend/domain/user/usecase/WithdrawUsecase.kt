package kr.flooding.backend.domain.user.usecase

import kr.flooding.backend.domain.auth.repository.RefreshTokenRepository
import kr.flooding.backend.domain.user.repository.jpa.UserJpaRepository
import kr.flooding.backend.global.exception.ExceptionEnum
import kr.flooding.backend.global.exception.HttpException
import kr.flooding.backend.global.exception.toPair
import kr.flooding.backend.global.util.UserUtil
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class WithdrawUsecase(
	private val userJpaRepository: UserJpaRepository,
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
			throw HttpException(ExceptionEnum.AUTH.WRONG_PASSWORD.toPair())
		}

		refreshTokenRepository.deleteById(id)
		userJpaRepository.delete(savedUser)
	}
}
