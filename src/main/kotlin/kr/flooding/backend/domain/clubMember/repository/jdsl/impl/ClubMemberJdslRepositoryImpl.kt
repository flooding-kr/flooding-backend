package kr.flooding.backend.domain.clubMember.repository.jdsl.impl

import com.linecorp.kotlinjdsl.dsl.jpql.jpql
import com.linecorp.kotlinjdsl.render.jpql.JpqlRenderContext
import com.linecorp.kotlinjdsl.support.spring.data.jpa.extension.createQuery
import jakarta.persistence.EntityManager
import kr.flooding.backend.domain.clubMember.entity.ClubMember
import kr.flooding.backend.domain.clubMember.repository.jdsl.ClubMemberJdslRepository
import kr.flooding.backend.domain.user.entity.User
import org.springframework.stereotype.Repository

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
}
