package kr.flooding.backend.domain.attendance.usecase

import kr.flooding.backend.domain.attendance.dto.web.request.AttendClubMyselfRequest
import kr.flooding.backend.domain.attendance.persistence.entity.Attendance
import kr.flooding.backend.domain.attendance.persistence.repository.jpa.AttendanceJpaRepository
import kr.flooding.backend.domain.club.enums.ClubStatus
import kr.flooding.backend.domain.club.persistence.repository.ClubRepository
import kr.flooding.backend.domain.clubMember.persistence.repository.jpa.ClubMemberJpaRepository
import kr.flooding.backend.domain.period.persistence.repository.PeriodRepository
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
class AttendClubMyselfUsecase(
	private val userUtil: UserUtil,
	private val attendanceJpaRepository: AttendanceJpaRepository,
	private val clubRepository: ClubRepository,
	private val clubMemberJpaRepository: ClubMemberJpaRepository,
	private val periodRepository: PeriodRepository,
) {
	fun execute(request: AttendClubMyselfRequest) {
		val club =
			clubRepository.findById(request.clubId).orElseThrow {
				HttpException(ExceptionEnum.CLUB.NOT_FOUND_CLUB.toPair())
			}

		if (club.status != ClubStatus.APPROVED) {
			throw HttpException(ExceptionEnum.CLUB.NOT_APPROVED_CLUB.toPair())
		}

		val currentUser = userUtil.getUser()

		if (!clubMemberJpaRepository.existsByClubIdAndUserId(request.clubId, currentUser.id)) {
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
			attendanceJpaRepository
				.findByStudentAndClubAndPeriodAndAttendedAt(currentUser, club, request.period, nowDate)
				.map { it.copy(isPresent = true, reason = null) }
				.orElse(
					Attendance(
						student = currentUser,
						period = request.period,
						club = club,
						attendedAt = nowDate,
					),
				)

		attendanceJpaRepository.save(attendance)
	}
}
