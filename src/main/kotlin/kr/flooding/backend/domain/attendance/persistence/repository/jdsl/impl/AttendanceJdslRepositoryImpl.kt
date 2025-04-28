package kr.flooding.backend.domain.attendance.persistence.repository.jdsl.impl

import com.linecorp.kotlinjdsl.dsl.jpql.jpql
import com.linecorp.kotlinjdsl.render.jpql.JpqlRenderContext
import com.linecorp.kotlinjdsl.support.spring.data.jpa.extension.createQuery
import jakarta.persistence.EntityManager
import kr.flooding.backend.domain.attendance.persistence.entity.Attendance
import kr.flooding.backend.domain.attendance.persistence.repository.jdsl.AttendanceJdslRepository
import kr.flooding.backend.domain.classroom.enums.BuildingType
import kr.flooding.backend.domain.classroom.persistence.entity.Classroom
import kr.flooding.backend.domain.club.persistence.entity.Club
import org.springframework.stereotype.Repository
import java.time.LocalDate
import java.util.*

@Repository
class AttendanceJdslRepositoryImpl(
	private val context: JpqlRenderContext,
	private val entityManager: EntityManager,
): AttendanceJdslRepository {
	override fun findWithStudentAndClubByAttendedAtAndPeriodAndClubId(
		attendedAt: LocalDate,
		period: Int,
		clubId: UUID
	): List<Attendance> {
		val query = jpql {
			select(
				entity(Attendance::class)
			).from(
				entity(Attendance::class),
				fetchJoin(Attendance::student),
				fetchJoin(Attendance::club),
			).where(
				path(Attendance::attendedAt).eq(attendedAt)
					.and(path(Attendance::period).eq(period))
					.and(path(Club::id).eq(clubId)),
			)
		}

		return entityManager.createQuery(query, context).resultList
	}
}