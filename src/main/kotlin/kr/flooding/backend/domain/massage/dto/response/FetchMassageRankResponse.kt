package kr.flooding.backend.domain.massage.dto.response

import kr.flooding.backend.domain.file.shared.PresignedUrlModel

class FetchMassageRankResponse (
    val schoolNumber: String,
    val name: String,
    val profileImage: PresignedUrlModel?
)
