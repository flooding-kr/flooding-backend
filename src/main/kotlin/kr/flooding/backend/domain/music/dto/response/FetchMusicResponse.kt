package kr.flooding.backend.domain.music.dto.response

import kr.flooding.backend.domain.music.persistence.entity.Music

data class FetchMusicResponse(
	val musicList: List<MusicResponse>,
) {
	companion object {
		fun toDto(musicList: List<Music>): FetchMusicResponse =
			FetchMusicResponse(
				musicList = musicList.map { MusicResponse.toDto(it) },
			)
	}
}
