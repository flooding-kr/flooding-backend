package kr.flooding.backend.domain.massage.persistence.repository.jdsl.impl

import com.linecorp.kotlinjdsl.dsl.jpql.jpql
import com.linecorp.kotlinjdsl.render.jpql.JpqlRenderContext
import com.linecorp.kotlinjdsl.support.spring.data.jpa.extension.createQuery
import jakarta.persistence.EntityManager
import kr.flooding.backend.domain.massage.persistence.entity.MassageReservation
import kr.flooding.backend.domain.massage.persistence.repository.jdsl.MassageReservationJdslRepository
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
class MassageReservationJdslRepositoryImpl (
	private val context: JpqlRenderContext,
	private val entityManager: EntityManager,
): MassageReservationJdslRepository {
	override fun findByCreatedAtBetweenAndIsCancelledAndOrderByCreatedAtDesc(
		startDateTime: LocalDateTime,
		endDateTime: LocalDateTime,
		isCancelled: Boolean
	): List<MassageReservation> {
		val query = jpql {
			select(
				entity(MassageReservation::class)
			).from(
				entity(MassageReservation::class)
			).whereAnd(
				path(MassageReservation::createdAt).between(startDateTime, endDateTime),
				path(MassageReservation::isCancelled).eq(isCancelled)
			).orderBy(
				path(MassageReservation::createdAt).desc()
			)
		}

		return entityManager.createQuery(query, context).resultList
	}
}