package kr.flooding.backend.domain.musicLike.persistence.repository.jpa

import kr.flooding.backend.domain.music.persistence.entity.Music
import kr.flooding.backend.domain.musicLike.persistence.entity.MusicLike
import kr.flooding.backend.domain.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface MusicLikeJpaRepository : JpaRepository<MusicLike, Long> {
	fun findByMusicAndUser(
        music: Music,
        user: User,
	): Optional<MusicLike>
}
