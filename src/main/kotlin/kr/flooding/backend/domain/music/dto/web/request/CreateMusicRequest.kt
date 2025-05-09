package kr.flooding.backend.domain.music.dto.web.request

import jakarta.validation.constraints.Pattern

class CreateMusicRequest(
	@Suppress("ktlint:standard:max-line-length")
	@field:Pattern(
		regexp = """^(https?:\/\/)?(www\.)?(youtube\.com|youtube-nocookie\.com|youtu\.be)\/(watch\?v=|embed\/|v\/)?([\w\-]+)(\S+)?$""",
		message = "유효한 유튜브 영상 URL을 입력해주세요.",
	)
	val musicUrl: String,
)
