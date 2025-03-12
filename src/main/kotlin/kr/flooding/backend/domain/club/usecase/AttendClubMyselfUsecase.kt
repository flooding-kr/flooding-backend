package kr.flooding.backend.domain.club.usecase

import kr.flooding.backend.domain.attendance.entity.Attendance
import kr.flooding.backend.domain.attendance.repository.AttendanceRepository
import kr.flooding.backend.domain.club.dto.request.AttendClubRequest
import kr.flooding.backend.domain.club.entity.ClubStatus
import kr.flooding.backend.domain.club.repository.ClubRepository
import kr.flooding.backend.domain.clubMember.repository.ClubMemberRepository
import kr.flooding.backend.global.exception.ExceptionEnum
import kr.flooding.backend.global.exception.HttpException
import kr.flooding.backend.global.exception.toPair
import kr.flooding.backend.global.util.UserUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
@Transactional
class AttendClubMyselfUsecase(
	private val userUtil: UserUtil,
	private val attendanceRepository: AttendanceRepository,
	private val clubRepository: ClubRepository,
	private val clubMemberRepository: ClubMemberRepository,
) {
	fun execute(request: AttendClubRequest) {
		val currentUser = userUtil.getUser()
		val nowDate = LocalDate.now()
		val club =
			clubRepository.findById(request.clubId).orElseThrow {
				HttpException(ExceptionEnum.CLUB.NOT_FOUND_CLUB.toPair())
			}

		if (club.status != ClubStatus.APPROVED) {
			throw HttpException(ExceptionEnum.CLUB.NOT_APPROVED_CLUB.toPair())
		}

		if (!clubMemberRepository.existsByClubIdAndUserId(request.clubId, currentUser.id)) {
			throw HttpException(ExceptionEnum.CLUB.NOT_CLUB_MEMBER.toPair())
		}

		// 이미 해당 날짜의 교시에 출석이 되어있고, 상태 (출석 or 미출석)이 같다면 상태가 동일하다는 예외처리
		val equalAttendance =
			attendanceRepository
				.findByStudentAndClubAndPeriodAndAttendedAt(currentUser, club, request.period, nowDate)

		if (equalAttendance.isPresent) {
			val attendance = equalAttendance.get()
			if (attendance.isPresent) {
				throw HttpException(ExceptionEnum.CLUB.NOT_CHANGED_ATTENDANCE_STATE.toPair())
			}
		}

		val attendance =
			attendanceRepository
				.findByStudentAndClubAndPeriodAndAttendedAt(currentUser, club, request.period, nowDate)
				.orElse(Attendance(student = currentUser, period = request.period, club = club, attendedAt = nowDate))

		attendance.isPresent = true
		attendance.reason = null
		attendanceRepository.save(attendance)
	}
}
