package kr.flooding.backend.domain.club.usecase

import kr.flooding.backend.domain.attendance.entity.Attendance
import kr.flooding.backend.domain.attendance.repository.AttendanceRepository
import kr.flooding.backend.domain.club.dto.request.AttendClubLeaderRequest
import kr.flooding.backend.domain.club.entity.ClubStatus
import kr.flooding.backend.domain.club.repository.ClubRepository
import kr.flooding.backend.domain.user.repository.UserRepository
import kr.flooding.backend.global.exception.ExceptionEnum
import kr.flooding.backend.global.exception.HttpException
import kr.flooding.backend.global.exception.toPair
import kr.flooding.backend.global.util.UserUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
@Transactional
class AttendClubLeaderUsecase(
	private val userUtil: UserUtil,
	private val clubRepository: ClubRepository,
	private val attendanceRepository: AttendanceRepository,
	private val userRepository: UserRepository,
) {
	fun execute(request: AttendClubLeaderRequest) {
		val currentUser = userUtil.getUser()
		val nowDate = LocalDate.now()
		val club =
			clubRepository.findById(request.clubId).orElseThrow {
				HttpException(ExceptionEnum.CLUB.NOT_FOUND_CLUB.toPair())
			}
		if (club.status != ClubStatus.APPROVED) {
			throw HttpException(ExceptionEnum.CLUB.NOT_APPROVED_CLUB.toPair())
		}

		if (currentUser != club.leader) {
			throw HttpException(ExceptionEnum.CLUB.NOT_CLUB_LEADER.toPair())
		}

		val equalAttendance =
			attendanceRepository
				.findByStudentAndClubAndPeriodAndAttendedAt(currentUser, club, request.period, nowDate)

		if (equalAttendance.isPresent) {
			val attendance = equalAttendance.get()
			if (attendance.isPresent) {
				throw HttpException(ExceptionEnum.CLUB.NOT_CHANGED_ATTENDANCE_STATE.toPair())
			}
		}

		val student =
			userRepository.findById(request.studentId).orElseThrow {
				HttpException(ExceptionEnum.USER.NOT_FOUND_USER.toPair())
			}

		val attendance =
			attendanceRepository
				.findByStudentAndClubAndPeriodAndAttendedAt(student, club, request.period, nowDate)
				.orElse(Attendance(student = student, period = request.period, club = club, attendedAt = nowDate))

		attendance.isPresent = true
		attendance.reason = null
		attendanceRepository.save(attendance)
	}
}
