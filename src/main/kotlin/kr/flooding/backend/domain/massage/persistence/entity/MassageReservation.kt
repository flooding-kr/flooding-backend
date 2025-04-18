package kr.flooding.backend.domain.massage.persistence.entity

import jakarta.persistence.*
import kr.flooding.backend.domain.user.persistence.entity.User
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import java.time.LocalDate
import java.time.LocalTime

@Entity
class MassageReservation(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, unique = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    val student: User,

    @CreationTimestamp
    @Column(nullable = false)
    val createdDate: LocalDate = LocalDate.now(),

    @CreationTimestamp
    @Column(nullable = false)
    val createdTime: LocalTime = LocalTime.now(),
) {
    @Column(nullable = false)
    var isCancelled: Boolean = false
        protected set

    fun cancelReservation() {
        isCancelled = true
    }
}