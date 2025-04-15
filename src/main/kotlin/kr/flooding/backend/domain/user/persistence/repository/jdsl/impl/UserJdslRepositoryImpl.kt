package kr.flooding.backend.domain.user.persistence.repository.jdsl.impl

import com.linecorp.kotlinjdsl.dsl.jpql.jpql
import com.linecorp.kotlinjdsl.render.jpql.JpqlRenderContext
import com.linecorp.kotlinjdsl.support.spring.data.jpa.extension.createQuery
import jakarta.persistence.EntityManager
import kr.flooding.backend.domain.role.enums.RoleType
import kr.flooding.backend.domain.role.persistence.entity.Role
import kr.flooding.backend.domain.user.enums.UserState
import kr.flooding.backend.domain.user.persistence.entity.StudentInfo
import kr.flooding.backend.domain.user.persistence.entity.User
import kr.flooding.backend.domain.user.persistence.repository.jdsl.UserJdslRepository
import kr.flooding.backend.global.security.model.UserCredential
import org.springframework.stereotype.Repository
import java.util.Optional
import java.util.UUID

@Repository
class UserJdslRepositoryImpl(
	private val context: JpqlRenderContext,
	private val entityManager: EntityManager,
) : UserJdslRepository {
	override fun findByNameLikeAndYearGreaterThanEqualAndRoleAndUserStateAndEmailVerifyStatus(
		name: String,
		year: Int,
		role: RoleType,
		userState: UserState,
		emailVerifyStatus: Boolean,
	): List<User> {
		val query =
			jpql {
				select(entity(User::class))
					.from(
						entity(User::class),
						fetchJoin(User::studentInfo),
						join(Role::class).on(entity(User::class).eq(path(Role::user))),
					).where(
						path(StudentInfo::year)
							.greaterThanOrEqualTo(year)
							.and(
								path(User::name).like("%$name%"),
							).and(path(Role::type).eq(role))
							.and(path(User::userState).eq(userState))
							.and(path(User::emailVerifyStatus).eq(emailVerifyStatus)),
					)
			}

		return entityManager.createQuery(query, context).resultList
	}

	override fun findByNameLikeAndRoleAndUserStateAndEmailVerifyStatus(
		name: String,
		role: RoleType,
		userState: UserState,
		emailVerifyStatus: Boolean,
	): List<User> {
		val query =
			jpql {
				select(entity(User::class))
					.from(
						entity(User::class),
						fetchJoin(User::studentInfo),
						join(Role::class).on(entity(User::class).eq(path(Role::user))),
					).where(
						path(User::name)
							.like("%$name%")
							.and(path(Role::type).eq(role))
							.and(path(User::userState).eq(userState))
							.and(path(User::emailVerifyStatus).eq(emailVerifyStatus)),
					)
			}

		return entityManager.createQuery(query, context).resultList
	}

	override fun findCredentialById(userId: UUID): Optional<UserCredential> {
		val query =
			jpql {
				selectNew<UserCredential>(
					path(User::id),
					path(User::email),
					path(User::encodedPassword),
				).from(entity(User::class)).where(path(User::id).eq(userId))
			}
		return Optional.ofNullable(entityManager.createQuery(query, context).resultList.firstOrNull())
	}
}
