package kr.flooding.backend.domain.attendance.usecase

import kr.flooding.backend.domain.attendance.dto.response.FetchAttendanceResponse
import kr.flooding.backend.domain.attendance.repository.jpa.AttendanceJpaRepository
import kr.flooding.backend.global.util.UserUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import kotlin.jvm.optionals.getOrNull

@Service
@Transactional
class FetchAttendanceUsecase(
	private val attendanceJpaRepository: AttendanceJpaRepository,
	private val userUtil: UserUtil,
) {
	fun execute(
		date: LocalDate,
		period: Int,
	): FetchAttendanceResponse {
		val currentUser = userUtil.getUser()
		val attendance = attendanceJpaRepository.findByAttendedAtAndPeriodAndStudent(date, period, currentUser)

		val isAttended =
			if (attendance.isPresent) {
				attendance.get().isPresent
			} else {
				false
			}

		return FetchAttendanceResponse(
			isAttended = isAttended,
			isChanged = attendance.isPresent,
			reason = attendance.getOrNull()?.reason,
		)
	}
}
