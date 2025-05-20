package kr.flooding.backend.domain.music.persistence.repository.jdsl.impl

import com.linecorp.kotlinjdsl.dsl.jpql.jpql
import com.linecorp.kotlinjdsl.render.jpql.JpqlRenderContext
import com.linecorp.kotlinjdsl.support.spring.data.jpa.extension.createQuery
import jakarta.persistence.EntityManager
import kr.flooding.backend.domain.music.dto.common.MusicWithLikeDto
import kr.flooding.backend.domain.music.enums.MusicOrderType
import kr.flooding.backend.domain.music.persistence.entity.Music
import kr.flooding.backend.domain.music.persistence.repository.jdsl.MusicJdslRepository
import kr.flooding.backend.domain.musicLike.persistence.entity.MusicLike
import kr.flooding.backend.domain.user.persistence.entity.User
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
class MusicJdslRepositoryImpl(
	private val context: JpqlRenderContext,
	private val entityManager: EntityManager,
) : MusicJdslRepository {
	override fun findAllByCreatedDateOrderByMusicOrderTypeAndUserContainsMusicLike(
		createdDate: LocalDate,
		musicOrderType: MusicOrderType,
		user: User
	): List<MusicWithLikeDto> {
		val query =
			jpql {
				selectNew<MusicWithLikeDto>(
					entity(Music::class),
					path(MusicLike::id).isNotNull()
				).from(
					entity(Music::class),
					fetchJoin(Music::proposer),
					leftJoin(MusicLike::class).on(
						path(MusicLike::music).eq(entity(Music::class)).and(
							path(MusicLike::user).eq(user)
						),
					)
				).where(
					path(Music::createdDate).eq(createdDate),
				).orderBy(
					when (musicOrderType) {
						MusicOrderType.LATEST -> path(Music::createdTime).desc()
						MusicOrderType.LIKES -> path(Music::likeCount).desc()
					},
				)
			}

		return entityManager.createQuery(query, context).resultList
	}
}
