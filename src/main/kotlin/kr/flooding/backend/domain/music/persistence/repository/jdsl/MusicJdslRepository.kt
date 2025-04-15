package kr.flooding.backend.domain.music.persistence.repository.jdsl

import kr.flooding.backend.domain.music.dto.request.MusicOrderType
import kr.flooding.backend.domain.music.persistence.entity.Music
import java.time.LocalDate

interface MusicJdslRepository {
	fun findAllByCreatedDateOrderByMusicOrderType(
		createdDate: LocalDate,
		musicOrderType: MusicOrderType,
	): List<Music>
}
