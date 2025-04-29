package kr.flooding.backend.domain.selfStudy.persistence.repository.jdsl.impl

import com.linecorp.kotlinjdsl.dsl.jpql.jpql
import com.linecorp.kotlinjdsl.render.jpql.JpqlRenderContext
import com.linecorp.kotlinjdsl.support.spring.data.jpa.extension.createQuery
import jakarta.persistence.EntityManager
import kr.flooding.backend.domain.selfStudy.persistence.entity.SelfStudyReservation
import kr.flooding.backend.domain.selfStudy.persistence.repository.jdsl.SelfStudyReservationJdslRepository
import kr.flooding.backend.domain.user.enums.Gender
import kr.flooding.backend.domain.user.persistence.entity.StudentInfo
import kr.flooding.backend.domain.user.persistence.entity.User
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
class SelfStudyReservationJdslRepositoryImpl(
    private val context: JpqlRenderContext,
    private val entityManager: EntityManager,
) : SelfStudyReservationJdslRepository {
    override fun findByCreatedByBetweenAndGradeAndClassroomAndGenderAndNameLikesAndIsCancelledFalse(
        createdAtBefore: LocalDateTime,
        createdAtAfter: LocalDateTime,
        year: Int?,
        classroom: Int?,
        gender: Gender?,
        name: String?,
    ): List<SelfStudyReservation> {
        val query =
            jpql {
                select(
                    entity(SelfStudyReservation::class),
                ).from(
                    entity(SelfStudyReservation::class),
                    fetchJoin(SelfStudyReservation::student),
                    fetchJoin(User::studentInfo),
                ).whereAnd(
                    path(SelfStudyReservation::createdAt).between(createdAtBefore, createdAtAfter),
                    path(SelfStudyReservation::isCancelled).eq(false),
                    year?.let { path(StudentInfo::year).eq(it) },
                    classroom?.let { path(StudentInfo::classroom).eq(it) },
                    gender?.let { path(User::gender).eq(it) },
                    name?.let { path(User::name).like("%$it%") },
                ).orderBy(
                    path(SelfStudyReservation::createdAt).asc(),
                )
            }

        return entityManager.createQuery(query, context).resultList
    }
}
