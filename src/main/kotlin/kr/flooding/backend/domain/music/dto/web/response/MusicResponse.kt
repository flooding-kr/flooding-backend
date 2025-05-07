package kr.flooding.backend.domain.music.dto.web.response

import kr.flooding.backend.domain.music.persistence.entity.Music
import kr.flooding.backend.domain.user.persistence.entity.User
import java.util.UUID

data class MusicResponse(
	val musicId: UUID?,
	val musicUrl: String,
	val musicName: String,
	val thumbnailImageUrl: String,
	val likeCount: Int,
	val proposer: MusicProposerResponse,
	val isAppliedByUser: Boolean,
	val isLikedByUser: Boolean,
) {
	companion object {
		fun toDto(
			music: Music,
			isAppliedByUser: Boolean,
			isLikedByUser: Boolean,
		): MusicResponse =
			MusicResponse(
				musicId = music.id,
				musicUrl = music.musicUrl,
				musicName = music.title,
				thumbnailImageUrl = music.thumbImageUrl,
				likeCount = music.likeCount,
				proposer = MusicProposerResponse.toDto(music.proposer),
				isAppliedByUser = isAppliedByUser,
				isLikedByUser = isLikedByUser,
			)
	}
}
