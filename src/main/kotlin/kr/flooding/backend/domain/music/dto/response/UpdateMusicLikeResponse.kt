package kr.flooding.backend.domain.music.dto.response

data class UpdateMusicLikeResponse(
	val likeCount: Int,
	val hasUserLiked: Boolean,
)
