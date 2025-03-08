package kr.flooding.backend.domain.homebaseParticipants.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import kr.flooding.backend.domain.homebase.entity.HomebaseGroup
import kr.flooding.backend.domain.user.entity.User
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import org.hibernate.annotations.UuidGenerator
import java.util.UUID

@Entity
class HomebaseParticipant(
    @Id
    @UuidGenerator
    val id: UUID? = null,

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    val user: User,

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    val homebaseGroup: HomebaseGroup,
)