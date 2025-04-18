package kr.flooding.backend.domain.user.dto.web.request

import jakarta.validation.constraints.Pattern

data class UpdateProfileRequest(
	@Suppress("ktlint:standard:max-line-length")
	@field:Pattern(
		regexp =
			"^images/\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{6}:[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}\\.webp$",
		message = "잘못된 이미지 경로 형식입니다.",
	)
	val profileImageKey: String,
)
