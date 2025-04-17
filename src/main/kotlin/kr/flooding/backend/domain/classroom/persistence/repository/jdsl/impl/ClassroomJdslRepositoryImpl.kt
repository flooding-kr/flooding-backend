package kr.flooding.backend.domain.classroom.persistence.repository.jdsl.impl

import com.linecorp.kotlinjdsl.dsl.jpql.jpql
import com.linecorp.kotlinjdsl.render.jpql.JpqlRenderContext
import com.linecorp.kotlinjdsl.support.spring.data.jpa.extension.createQuery
import jakarta.persistence.EntityManager
import kr.flooding.backend.domain.classroom.persistence.entity.BuildingType
import kr.flooding.backend.domain.classroom.persistence.entity.Classroom
import kr.flooding.backend.domain.classroom.persistence.repository.jdsl.ClassroomJdslRepository
import org.springframework.stereotype.Repository

@Repository
class ClassroomJdslRepositoryImpl(
	private val context: JpqlRenderContext,
	private val entityManager: EntityManager,
) : ClassroomJdslRepository {
	override fun findWithTeacherByFloorAndBuildingTypeAndInName(
		floor: Int,
		buildingType: BuildingType,
		name: String,
	): List<Classroom> {
		val query =
			jpql {
				select(
					entity(Classroom::class),
				).from(
					entity(Classroom::class),
					leftFetchJoin(Classroom::teacher),
				).where(
					path(Classroom::floor)
						.eq(floor)
						.and(path(Classroom::buildingType).eq(buildingType))
						.and(path(Classroom::name).like("%$name%")),
				)
			}

		return entityManager.createQuery(query, context).resultList
	}
}
