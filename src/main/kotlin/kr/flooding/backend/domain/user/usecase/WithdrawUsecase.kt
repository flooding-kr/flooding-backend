package kr.flooding.backend.domain.user.usecase

import kr.flooding.backend.domain.auth.persistence.repository.RefreshTokenRepository
import kr.flooding.backend.domain.user.persistence.entity.UserDeletion
import kr.flooding.backend.domain.user.persistence.repository.jpa.UserDeletionJpaRepository
import kr.flooding.backend.global.exception.ExceptionEnum
import kr.flooding.backend.global.exception.HttpException
import kr.flooding.backend.global.exception.toPair
import kr.flooding.backend.global.util.UserUtil
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
@Transactional
class WithdrawUsecase(
	private val userDeletionJpaRepository: UserDeletionJpaRepository,
	private val refreshTokenRepository: RefreshTokenRepository,
	private val userUtil: UserUtil,
	private val passwordEncoder: PasswordEncoder,
) {
	fun execute(password: String) {
		val currentUser = userUtil.getUser()

		val isComparePassword = passwordEncoder.matches(password, currentUser.encodedPassword)
		if (!isComparePassword) {
			throw HttpException(ExceptionEnum.AUTH.WRONG_PASSWORD.toPair())
		}

		val isExistsUserDeletion = userDeletionJpaRepository.existsByUser(currentUser)
		if(isExistsUserDeletion) {
			throw HttpException(ExceptionEnum.USER.EXISTS_USER_DELETION.toPair())
		}

		val id = requireNotNull(currentUser.id) { "id cannot be null" }

		refreshTokenRepository.deleteById(id)
		userDeletionJpaRepository.save(
			UserDeletion(
				user = currentUser,
				deletedDate = LocalDate.now().plusDays(30),
			)
		)
	}
}
