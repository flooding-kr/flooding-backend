package kr.flooding.backend.domain.notice.persistence.repository.jdsl.impl

import com.linecorp.kotlinjdsl.dsl.jpql.jpql
import com.linecorp.kotlinjdsl.render.jpql.JpqlRenderContext
import com.linecorp.kotlinjdsl.support.spring.data.jpa.extension.createQuery
import jakarta.persistence.EntityManager
import kr.flooding.backend.domain.notice.persistence.entity.Notice
import kr.flooding.backend.domain.notice.persistence.entity.NoticeType
import kr.flooding.backend.domain.notice.persistence.repository.jdsl.NoticeJdslRepository
import org.springframework.stereotype.Repository

@Repository
class NoticeJdslRepositoryImpl(
    private val context: JpqlRenderContext,
    private val entityManager: EntityManager,
): NoticeJdslRepository {
    override fun findAllByNoticeTypesOrderByCreatedAt(noticeTypes: List<NoticeType>): List<Notice> {
        val query =
            jpql {
                select(
                    entity(Notice::class)
                ).from(
                    entity(Notice::class)
                ).apply {
                    if (noticeTypes.isNotEmpty()) {
                        where(path(Notice::type).`in`(noticeTypes))
                    }
                }.orderBy(
                    path(Notice::createdAt).desc()
                )
            }

        return entityManager.createQuery(query, context).resultList
    }
}