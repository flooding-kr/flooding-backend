package kr.flooding.backend.domain.homebase.repository.jdsl.impl

import com.linecorp.kotlinjdsl.dsl.jpql.jpql
import com.linecorp.kotlinjdsl.render.jpql.JpqlRenderContext
import com.linecorp.kotlinjdsl.support.spring.data.jpa.extension.createQuery
import jakarta.persistence.EntityManager
import kr.flooding.backend.domain.classroom.entity.Classroom
import kr.flooding.backend.domain.homebase.entity.HomebaseGroup
import kr.flooding.backend.domain.homebase.repository.jdsl.HomebaseGroupJdslRepository
import kr.flooding.backend.domain.homebaseParticipants.entity.HomebaseParticipant
import kr.flooding.backend.domain.homebaseTable.entity.HomebaseTable
import kr.flooding.backend.domain.user.entity.User
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
class HomebaseGroupJdslRepositoryImpl(
	private val context: JpqlRenderContext,
	private val entityManager: EntityManager,
) : HomebaseGroupJdslRepository {
	override fun findWithHomebaseTableWithHomebaseAndProposerByProposerOrParticipantsAndAttendedAt(
		student: User,
		attendedAt: LocalDate,
	): List<HomebaseGroup> {
		val query =
			jpql {
				select(
					entity(HomebaseGroup::class),
				).from(
					entity(HomebaseGroup::class),
					leftJoin(HomebaseGroup::participants),
					fetchJoin(HomebaseGroup::proposer),
					fetchJoin(HomebaseGroup::homebaseTable),
					fetchJoin(HomebaseTable::homebase),
				).where(
					path(HomebaseGroup::proposer)
						.eq(student)
						.or(path(HomebaseParticipant::user).eq(student))
						.and(path(HomebaseGroup::attendedAt).eq(attendedAt)),
				)
			}

		return entityManager.createQuery(query, context).resultList
	}

	override fun findWithParticipantsAndProposerByPeriodAndHomebaseTableHomebaseFloorAndAttendedAt(
		period: Int,
		floor: Int,
		attendedAt: LocalDate,
	): List<HomebaseGroup> {
		val query =
			jpql {
				select(
					entity(HomebaseGroup::class),
				).from(
					entity(HomebaseGroup::class),
					fetchJoin(HomebaseGroup::proposer),
					join(HomebaseGroup::homebaseTable),
					join(HomebaseTable::homebase),
				).where(
					path(HomebaseGroup::period)
						.eq(period)
						.and(path(Classroom::floor).eq(floor))
						.and(path(HomebaseGroup::attendedAt).eq(attendedAt)),
				)
			}

		return entityManager.createQuery(query, context).resultList
	}
}
