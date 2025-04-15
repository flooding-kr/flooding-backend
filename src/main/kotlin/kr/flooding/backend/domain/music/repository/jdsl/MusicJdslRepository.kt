package kr.flooding.backend.domain.music.repository.jdsl

import kr.flooding.backend.domain.music.dto.request.MusicOrderType
import kr.flooding.backend.domain.music.entity.Music
import java.time.LocalDate

interface MusicJdslRepository {
    fun findAllByCreatedDateOrderByMusicOrderType(date: LocalDate, musicOrderType: MusicOrderType): List<Music>
}