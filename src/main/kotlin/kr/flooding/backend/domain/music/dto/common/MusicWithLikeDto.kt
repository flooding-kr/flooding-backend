package kr.flooding.backend.domain.music.dto.common

import kr.flooding.backend.domain.music.persistence.entity.Music

class MusicWithLikeDto (
	val music: Music,
	val isLikedByUser: Boolean,
)