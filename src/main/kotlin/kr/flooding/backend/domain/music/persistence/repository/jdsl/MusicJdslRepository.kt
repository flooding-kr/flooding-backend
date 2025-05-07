package kr.flooding.backend.domain.music.persistence.repository.jdsl

import kr.flooding.backend.domain.music.dto.common.MusicWithLikeDto
import kr.flooding.backend.domain.music.enums.MusicOrderType
import kr.flooding.backend.domain.music.persistence.entity.Music
import kr.flooding.backend.domain.user.persistence.entity.User
import java.time.LocalDate

interface MusicJdslRepository {
	fun findAllByCreatedDateOrderByMusicOrderTypeAndUserContainsMusicLike(
		createdDate: LocalDate,
		musicOrderType: MusicOrderType,
		user: User
	): List<MusicWithLikeDto>
}
