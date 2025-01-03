package team.gsm.flooding.domain.user.usecase

import team.gsm.flooding.domain.user.entity.User
import team.gsm.flooding.domain.user.repository.UserRepository
import team.gsm.flooding.global.util.UserUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class FetchUserUsecase (
	private val userRepository: UserRepository,
	private val userUtil: UserUtil
) {
	fun execute(): User { // TODO 임시 확인용
		return userUtil.getUser()
	}
}