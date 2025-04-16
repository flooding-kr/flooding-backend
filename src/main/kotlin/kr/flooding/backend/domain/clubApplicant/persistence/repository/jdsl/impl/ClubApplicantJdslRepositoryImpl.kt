package kr.flooding.backend.domain.clubApplicant.persistence.repository.jdsl.impl

import com.linecorp.kotlinjdsl.dsl.jpql.jpql
import com.linecorp.kotlinjdsl.render.jpql.JpqlRenderContext
import com.linecorp.kotlinjdsl.support.spring.data.jpa.extension.createQuery
import jakarta.persistence.EntityManager
import kr.flooding.backend.domain.club.persistence.entity.Club
import kr.flooding.backend.domain.clubApplicant.persistence.entity.ClubApplicant
import kr.flooding.backend.domain.clubApplicant.persistence.repository.jdsl.ClubApplicantJdslRepository
import kr.flooding.backend.domain.user.persistence.entity.User
import org.springframework.stereotype.Repository
import java.util.Optional
import java.util.UUID

@Repository
class ClubApplicantJdslRepositoryImpl(
	private val context: JpqlRenderContext,
	private val entityManager: EntityManager,
) : ClubApplicantJdslRepository {
	override fun findWithClubAndUserByClub(club: Club): List<ClubApplicant> {
		val query =
			jpql {
				select(entity(ClubApplicant::class))
					.from(
						entity(ClubApplicant::class),
						fetchJoin(ClubApplicant::user),
						fetchJoin(ClubApplicant::club),
					).where(path(ClubApplicant::club).eq(club))
			}

		return entityManager.createQuery(query, context).resultList
	}

	override fun findWithClubAndUserByUserAndClub(
		user: User,
		club: Club,
	): Optional<ClubApplicant> {
		val query =
			jpql {
				select(entity(ClubApplicant::class))
					.from(
						entity(ClubApplicant::class),
						fetchJoin(ClubApplicant::user),
						fetchJoin(ClubApplicant::club),
					).where(path(ClubApplicant::club).eq(club).and(path(ClubApplicant::user).eq(user)))
			}

		return Optional.ofNullable(entityManager.createQuery(query, context).resultList.firstOrNull())
	}

	override fun findById(id: UUID): Optional<ClubApplicant> {
		val query =
			jpql {
				select(entity(ClubApplicant::class))
					.from(
						entity(ClubApplicant::class),
						fetchJoin(ClubApplicant::user),
						fetchJoin(ClubApplicant::club),
					).where(path(ClubApplicant::id).eq(id))
			}

		return Optional.ofNullable(entityManager.createQuery(query, context).resultList.firstOrNull())
	}
}
