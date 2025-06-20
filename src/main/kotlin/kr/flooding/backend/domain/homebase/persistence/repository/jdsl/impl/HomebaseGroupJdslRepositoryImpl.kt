package kr.flooding.backend.domain.homebase.persistence.repository.jdsl.impl

import com.linecorp.kotlinjdsl.dsl.jpql.jpql
import com.linecorp.kotlinjdsl.render.jpql.JpqlRenderContext
import com.linecorp.kotlinjdsl.support.spring.data.jpa.extension.createQuery
import jakarta.persistence.EntityManager
import kr.flooding.backend.domain.classroom.persistence.entity.Classroom
import kr.flooding.backend.domain.homebase.persistence.entity.HomebaseGroup
import kr.flooding.backend.domain.homebase.persistence.repository.jdsl.HomebaseGroupJdslRepository
import kr.flooding.backend.domain.homebaseParticipants.persistence.entity.HomebaseParticipant
import kr.flooding.backend.domain.homebaseTable.persistence.entity.HomebaseTable
import kr.flooding.backend.domain.user.persistence.entity.User
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
					path(HomebaseGroup::attendedAt).eq(attendedAt).and(
						path(HomebaseGroup::proposer).eq(student)
							.or(path(HomebaseParticipant::user).eq(student))
					)
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
					path(HomebaseGroup::attendedAt).eq(attendedAt).and(
						path(HomebaseGroup::period).eq(period)
							.and(path(Classroom::floor).eq(floor))
					)
				)
			}

		return entityManager.createQuery(query, context).resultList
	}

	override fun existsByAttendedAtAndPeriodAndProposerInOrParticipantIn(
		attendedAt: LocalDate,
		period: Int,
		proposer: List<User>,
		participant: List<User>
	): Boolean {
		val query =
			jpql {
				select(
					count(entity(HomebaseGroup::class))
				).from(
					entity(HomebaseGroup::class),
					leftJoin(HomebaseGroup::participants)
				).whereAnd(
					path(HomebaseGroup::attendedAt).eq(attendedAt),
					path(HomebaseGroup::period).eq(period),
					or(
						path(HomebaseGroup::proposer).`in`(proposer),
						path(HomebaseParticipant::user).`in`(participant),
					),
				)
			}

		val count = entityManager.createQuery(query, context).singleResult
		return count > 0
	}
}
