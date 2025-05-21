package kr.flooding.backend.domain.music.persistence.repository.jpa

import kr.flooding.backend.domain.music.persistence.entity.Music
import kr.flooding.backend.domain.user.persistence.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDate
import java.util.Optional
import java.util.UUID

interface MusicJpaRepository : JpaRepository<Music, UUID> {
    fun existsByProposerAndCreatedDate(
        proposer: User,
        createdDate: LocalDate,
    ): Boolean

    fun findByProposerAndCreatedDate(
        proposer: User,
        createdDate: LocalDate,
    ): Optional<Music>

    fun deleteAllByCreatedDateBefore(createdDate: LocalDate)
}
