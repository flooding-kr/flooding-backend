package kr.flooding.backend.domain.selfStudy.persistence.entity

import jakarta.persistence.*
import kr.flooding.backend.domain.user.persistence.entity.User
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import org.hibernate.annotations.UuidGenerator
import java.time.LocalDate
import java.util.UUID

@Entity
class SelfStudyBan(
    @Id
    @UuidGenerator
    val id: UUID? = null,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    val student: User,

    @Column(nullable = false)
    val resumeDate: LocalDate
)