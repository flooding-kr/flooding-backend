package kr.flooding.backend.domain.attendance.usecase

import kr.flooding.backend.domain.attendance.entity.Attendance
import kr.flooding.backend.domain.attendance.repository.AttendanceRepository
import kr.flooding.backend.domain.club.dto.request.AbsenceClubMyselfRequest
import kr.flooding.backend.domain.club.entity.ClubStatus
import kr.flooding.backend.domain.club.repository.ClubRepository
import kr.flooding.backend.domain.period.repository.PeriodRepository
import kr.flooding.backend.global.exception.ExceptionEnum
import kr.flooding.backend.global.exception.HttpException
import kr.flooding.backend.global.exception.toPair
import kr.flooding.backend.global.util.UserUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.time.LocalTime

@Service
@Transactional
class AbsenceClubMyselfUsecase(
	private val userUtil: UserUtil,
	private val attendanceRepository: AttendanceRepository,
	private val clubRepository: ClubRepository,
	private val periodRepository: PeriodRepository,
) {
	fun execute(request: AbsenceClubMyselfRequest) {
		val club =
			clubRepository.findById(request.clubId).orElseThrow {
				HttpException(ExceptionEnum.CLUB.NOT_FOUND_CLUB.toPair())
			}

		if (club.status != ClubStatus.APPROVED) {
			throw HttpException(ExceptionEnum.CLUB.NOT_APPROVED_CLUB.toPair())
		}

		val currentUser = userUtil.getUser()

		if (!attendanceRepository.existsByStudentAndClub(currentUser, club)) {
			throw HttpException(ExceptionEnum.CLUB.NOT_CLUB_MEMBER.toPair())
		}

		val nowDate = LocalDate.now()
		val nowTime = LocalTime.now()
		val period =
			periodRepository.findById(request.period).orElseThrow {
				HttpException(ExceptionEnum.ATTENDANCE.NOT_FOUND_PERIOD_INFO.toPair())
			}

		if (nowTime.isBefore(period.startTime) || nowTime.isAfter(period.endTime)) {
			throw HttpException(ExceptionEnum.ATTENDANCE.ATTENDANCE_OUT_OF_TIME_RANGE.toPair())
		}

		val attendance =
			attendanceRepository
				.findByStudentAndClubAndPeriodAndAttendedAt(currentUser, club, request.period, nowDate)
				.map { it.copy(isPresent = false, reason = request.reason) }
				.orElse(
					Attendance(
						student = currentUser,
						period = request.period,
						club = club,
						attendedAt = nowDate,
						isPresent = false,
						reason = request.reason,
					),
				)

		attendanceRepository.save(attendance)
	}
}
