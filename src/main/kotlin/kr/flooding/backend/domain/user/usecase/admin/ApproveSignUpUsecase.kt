package kr.flooding.backend.domain.user.usecase.admin

import kr.flooding.backend.domain.user.persistence.repository.jpa.UserJpaRepository
import kr.flooding.backend.global.exception.ExceptionEnum
import kr.flooding.backend.global.exception.HttpException
import kr.flooding.backend.global.exception.toPair
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
@Transactional
class ApproveSignUpUsecase(
	private val userJpaRepository: UserJpaRepository,
) {
	fun execute(userId: UUID) {
		val user =
			userJpaRepository.findById(userId).orElseThrow {
				HttpException(ExceptionEnum.USER.NOT_FOUND_USER.toPair())
			}
		user.setApprovedState()
	}
}
