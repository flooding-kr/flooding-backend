package kr.flooding.backend.domain.music.usecase

import kr.flooding.backend.domain.music.dto.web.response.UpdateMusicLikeResponse
import kr.flooding.backend.domain.music.usecase.helper.UpdateMusicLikeRetryHelper
import kr.flooding.backend.global.util.UserUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
@Transactional
class UpdateMusicLikeUsecase(
	private val userUtil: UserUtil,
	private val updateMusicLikeRetryHelper: UpdateMusicLikeRetryHelper,
) {
	fun execute(musicId: UUID): UpdateMusicLikeResponse {
		val currentUser = userUtil.getUser()
		return updateMusicLikeRetryHelper.execute(
			musicId = musicId,
			currentUser = currentUser,
		)
	}
}
