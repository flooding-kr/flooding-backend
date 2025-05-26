package kr.flooding.backend.domain.selfStudy.persistence.entity

import jakarta.persistence.*
import kr.flooding.backend.domain.user.persistence.entity.User
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import java.time.LocalDate

@Entity
class SelfStudyBan(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    val student: User,

    @Column(nullable = false)
    val resumeDate: LocalDate
)