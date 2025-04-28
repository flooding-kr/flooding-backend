package kr.flooding.backend.domain.clubMember.persistence.repository.jdsl.impl

import com.linecorp.kotlinjdsl.dsl.jpql.jpql
import com.linecorp.kotlinjdsl.render.jpql.JpqlRenderContext
import com.linecorp.kotlinjdsl.support.spring.data.jpa.extension.createQuery
import jakarta.persistence.EntityManager
import kr.flooding.backend.domain.club.persistence.entity.Club
import kr.flooding.backend.domain.clubMember.persistence.entity.ClubMember
import kr.flooding.backend.domain.clubMember.persistence.repository.jdsl.ClubMemberJdslRepository
import kr.flooding.backend.domain.user.persistence.entity.User
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class ClubMemberJdslRepositoryImpl(
	private val context: JpqlRenderContext,
	private val entityManager: EntityManager,
) : ClubMemberJdslRepository {
	override fun findByUser(user: User): List<ClubMember> {
		val query =
			jpql {
				select(entity(ClubMember::class))
					.from(
						entity(ClubMember::class),
						fetchJoin(ClubMember::user),
						fetchJoin(ClubMember::club),
					).where(path(ClubMember::user).eq(user))
			}

		return entityManager.createQuery(query, context).resultList
	}

	override fun findWithUserAndClubByClubIdAndUserIsNot(clubId: UUID, user: User): List<ClubMember> {
		val query =
			jpql {
				select(
					entity(ClubMember::class)
				).from(
					entity(ClubMember::class),
					fetchJoin(ClubMember::user),
					fetchJoin(ClubMember::club),
				).where(
					path(Club::id).eq(clubId).and(
						path(ClubMember::user).eq(user)
					)
				)
			}

		return entityManager.createQuery(query, context).resultList
	}

	override fun findWithUserAndClubByClubId(clubId: UUID): List<ClubMember> {
		val query =
			jpql {
				select(
					entity(ClubMember::class)
				).from(
					entity(ClubMember::class),
					fetchJoin(ClubMember::user),
					fetchJoin(ClubMember::club),
				).where(
					path(Club::id).eq(clubId)
				)
			}

		return entityManager.createQuery(query, context).resultList
	}
}
