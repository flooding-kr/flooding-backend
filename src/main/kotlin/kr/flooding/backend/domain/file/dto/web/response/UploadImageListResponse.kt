package kr.flooding.backend.domain.file.dto.web.response

import kr.flooding.backend.domain.file.shared.PresignedUrlModel

class UploadImageListResponse(
	val images: List<PresignedUrlModel>,
)
