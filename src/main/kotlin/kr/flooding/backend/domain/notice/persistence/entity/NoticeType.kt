package kr.flooding.backend.domain.notice.persistence.entity

enum class NoticeType(
    val description: String,
) {
    DORMITORY("기숙사"),
    HOMEBASE("홈베이스"),
    CLUB("동아리"),
}