package kr.flooding.backend.domain.selfStudy.persistence.repository.jdsl.impl

import com.linecorp.kotlinjdsl.dsl.jpql.jpql
import com.linecorp.kotlinjdsl.render.jpql.JpqlRenderContext
import com.linecorp.kotlinjdsl.support.spring.data.jpa.extension.createQuery
import jakarta.persistence.EntityManager
import jakarta.persistence.LockModeType
import kr.flooding.backend.domain.selfStudy.persistence.entity.SelfStudyReservation
import kr.flooding.backend.domain.selfStudy.persistence.entity.SelfStudyRoom
import kr.flooding.backend.domain.selfStudy.persistence.repository.jdsl.SelfStudyReservationJdslRepository
import kr.flooding.backend.domain.selfStudy.persistence.repository.jdsl.SelfStudyRoomJdslRepository
import kr.flooding.backend.domain.user.enums.Gender
import kr.flooding.backend.domain.user.persistence.entity.StudentInfo
import kr.flooding.backend.domain.user.persistence.entity.User
import org.springframework.data.jpa.repository.Lock
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import java.util.*

@Repository
class SelfStudyRoomJdslRepositoryImpl(
    private val context: JpqlRenderContext,
    private val entityManager: EntityManager,
) : SelfStudyRoomJdslRepository {

    override fun findByIdIsNotNullWithPessimisticLock(): Optional<SelfStudyRoom> {
        val query = jpql {
            select(
                entity(SelfStudyRoom::class),
            ).from(
                entity(SelfStudyRoom::class),
            ).whereAnd(
                path(SelfStudyRoom::id).isNotNull(),
            )
        }

        return entityManager
            .createQuery(query, context)
            .setLockMode(LockModeType.PESSIMISTIC_WRITE)
            .resultList
            .firstOrNull()
            .let { Optional.ofNullable(it) }
    }
}
