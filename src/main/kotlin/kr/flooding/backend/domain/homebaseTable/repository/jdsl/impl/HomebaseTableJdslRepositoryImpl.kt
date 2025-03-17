package kr.flooding.backend.domain.classroom.repository.jdsl.impl

import com.linecorp.kotlinjdsl.dsl.jpql.jpql
import com.linecorp.kotlinjdsl.render.jpql.JpqlRenderContext
import com.linecorp.kotlinjdsl.support.spring.data.jpa.extension.createQuery
import jakarta.persistence.EntityManager
import kr.flooding.backend.domain.classroom.entity.Classroom
import kr.flooding.backend.domain.classroom.entity.HomebaseTable
import kr.flooding.backend.domain.classroom.repository.jdsl.HomebaseTableJdslRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
class HomebaseTableJdslRepositoryImpl(
	private val context: JpqlRenderContext,
	private val entityManager: EntityManager,
) : HomebaseTableJdslRepository {
	override fun findWithHomebaseByTableNumberAndFloor(
		tableNumber: Int,
		homebaseFloor: Int,
	): Optional<HomebaseTable> {
		val query =
			jpql {
				select(
					entity(HomebaseTable::class),
				).from(
					entity(HomebaseTable::class),
					fetchJoin(HomebaseTable::homebase),
				).where(
					path(HomebaseTable::tableNumber).eq(tableNumber).and(path(Classroom::floor).eq(homebaseFloor)),
				)
			}

		val result = entityManager.createQuery(query, context).resultList.firstOrNull()
		return Optional.ofNullable(result)
	}

	override fun findWithHomebaseByFloor(homebaseFloor: Int): List<HomebaseTable> {
		val query =
			jpql {
				select(
					entity(HomebaseTable::class),
				).from(
					entity(HomebaseTable::class),
					fetchJoin(HomebaseTable::homebase),
				).where(
					path(Classroom::floor).eq(homebaseFloor),
				)
			}

		return entityManager.createQuery(query, context).resultList
	}
}
