package kr.flooding.backend.domain.musicLike.repository.jpa

import kr.flooding.backend.domain.music.entity.Music
import kr.flooding.backend.domain.musicLike.entity.MusicLike
import kr.flooding.backend.domain.user.persistence.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface MusicLikeJpaRepository : JpaRepository<MusicLike, Long> {
	fun findByMusicAndUser(
		music: Music,
		user: User,
	): Optional<MusicLike>
}
