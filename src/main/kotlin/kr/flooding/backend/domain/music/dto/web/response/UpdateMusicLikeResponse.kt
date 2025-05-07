package kr.flooding.backend.domain.music.dto.web.response

data class UpdateMusicLikeResponse(
	val likeCount: Int,
	val hasUserLiked: Boolean,
)
