package kr.flooding.backend.domain.selfStudy.dto.response

class SelfStudyStatusResponse(
    val currentCount: Int,
    val limit: Int,
    val isFull: Boolean = false,
)
