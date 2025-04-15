package kr.flooding.backend.domain.music.persistence.repository.jdsl.impl

import com.linecorp.kotlinjdsl.dsl.jpql.jpql
import com.linecorp.kotlinjdsl.render.jpql.JpqlRenderContext
import com.linecorp.kotlinjdsl.support.spring.data.jpa.extension.createQuery
import jakarta.persistence.EntityManager
import kr.flooding.backend.domain.music.dto.request.MusicOrderType
import kr.flooding.backend.domain.music.persistence.entity.Music
import kr.flooding.backend.domain.music.persistence.repository.jdsl.MusicJdslRepository
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
class MusicJdslRepositoryImpl(
	private val context: JpqlRenderContext,
	private val entityManager: EntityManager,
) : MusicJdslRepository {
	override fun findAllByCreatedDateOrderByMusicOrderType(
		createdDate: LocalDate,
		musicOrderType: MusicOrderType,
	): List<Music> {
		val createdAtStart = createdDate.atStartOfDay()
		val createdAtEnd = createdAtStart.plusDays(1)

		val query =
			jpql {
				select(
					entity(Music::class),
				).from(
					entity(Music::class),
					fetchJoin(Music::proposer),
				).where(
					path(Music::createdAt).between(createdAtStart, createdAtEnd),
				).orderBy(
					when (musicOrderType) {
						MusicOrderType.LATEST -> path(Music::createdAt).desc()
						MusicOrderType.LIKES -> path(Music::likeCount).desc()
					},
				)
			}
		return entityManager.createQuery(query, context).resultList
	}
}
