package kr.flooding.backend.domain.music.scheduler

import kr.flooding.backend.domain.music.usecase.student.DeleteOldMusicUsecase
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class DeleteOldMusicScheduler(
	private val deleteOldMusicusecase: DeleteOldMusicUsecase,
) {
    @Scheduled(cron = "0 0 1 1 * *", zone = "Asia/Seoul")
    fun deleteOldMusic() {
        deleteOldMusicusecase.execute()
    }
}
