package kr.flooding.backend.domain.music.dto.response

import kr.flooding.backend.domain.music.entity.Music

data class MusicResponse(
    val musicUrl: String,
    val musicName: String,
    val thumbnailImageUrl: String,
    val likeCount: Int,
    val proposer: MusicProposerResponse
) {
    companion object {
        fun toDto(music: Music): MusicResponse =
            MusicResponse(
                musicUrl = music.musicUrl,
                musicName = music.title,
                thumbnailImageUrl = music.thumbImageUrl,
                likeCount = music.likeCount,
                proposer = MusicProposerResponse.toDto(music.proposer)
            )
    }
}