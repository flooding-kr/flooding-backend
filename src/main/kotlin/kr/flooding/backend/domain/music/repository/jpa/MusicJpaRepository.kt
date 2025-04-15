package kr.flooding.backend.domain.music.repository.jpa

import kr.flooding.backend.domain.music.entity.Music
import kr.flooding.backend.domain.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime
import java.util.UUID

interface MusicJpaRepository : JpaRepository<Music, UUID> {
	fun existsByProposerAndCreatedAtBetween(
		proposer: User,
		startTime: LocalDateTime,
		endTime: LocalDateTime,
	): Boolean
}
