package kr.flooding.backend.domain.user.usecase

import kr.flooding.backend.domain.user.dto.web.request.UpdateProfileRequest
import kr.flooding.backend.global.util.UserUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UpdateProfileImageUsecase(
	private val userUtil: UserUtil,
) {
	fun execute(request: UpdateProfileRequest) {
		val user = userUtil.getUser()
		user.updateProfileImage(request.profileImageKey)
	}
}
