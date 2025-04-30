package kr.flooding.backend.domain.selfStudy.dto.response

import kr.flooding.backend.domain.file.shared.PresignedUrlModel

class FetchSelfStudyResponse(
    val studentNumber: String,
    val name: String,
    val profileImage: PresignedUrlModel?,
)
