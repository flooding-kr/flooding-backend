package kr.flooding.backend.domain.music.repository.jpa

import kr.flooding.backend.domain.music.entity.Music
import kr.flooding.backend.domain.user.persistence.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDate
import java.util.UUID

interface MusicJpaRepository : JpaRepository<Music, UUID> {
	fun existsByProposerAndCreatedAt(
		proposer: User,
		createdAt: LocalDate,
	): Boolean
}
