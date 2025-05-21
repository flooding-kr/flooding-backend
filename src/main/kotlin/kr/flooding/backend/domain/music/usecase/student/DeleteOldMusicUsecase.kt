package kr.flooding.backend.domain.music.usecase.student

import jakarta.transaction.Transactional
import kr.flooding.backend.domain.music.persistence.repository.jpa.MusicJpaRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.LocalTime

@Service
@Transactional
class DeleteOldMusicUsecase(
    private val musicJpaRepository: MusicJpaRepository,
) {
    fun execute() {
        val firstMomentOfThisMonth =
            LocalDateTime
                .now()
                .withDayOfMonth(1)
                .with(LocalTime.MIN)

        musicJpaRepository.deleteAllByCreatedDateBefore(firstMomentOfThisMonth.toLocalDate())
    }
}
